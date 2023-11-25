import re
from random import choice, randint, sample
from src.repository.baserepository.person_repo import PersonRepo
from src.repository.baserepository.activity_repo import ActivityRepo
from src.myfunctions import *
from src.domain.entities import *
from datetime import datetime

from src.myexceptions import *


class Service:
    def __init__(self, person_repo=None, discipline_repo=None):
        self._person_repo = person_repo
        self._activity_repo = discipline_repo
        if person_repo is None:
            self._person_repo = PersonRepo(self._generate_persons())
        if discipline_repo is None:
            self._activity_repo = ActivityRepo(self._generate_activities())

    @property
    def persons(self):
        return self._person_repo.persons

    @property
    def activities(self):
        return self._activity_repo.activities

    @staticmethod
    def _generate_persons():
        """
        Generates a list with 20 random persons
        :return: list with 20 objects of type Person
        """
        names = Person.list_of_persons()
        persons = []
        for i in range(min(20, len(names))):
            name = choice(names)
            names.remove(name)
            number = randint(100000000,999999999)
            phone_number = "0" + str(number)
            person_id = randrange(1, 100)
            while person_id in [x.get_person_id for x in persons]:
                person_id = randrange(1, 100)
            persons.append(Person(person_id, name, phone_number))
        return persons

    @staticmethod
    def _generate_activities():
        """
        Generates a list with 20 random activities
        :return: list with 20 objects of type Activity
        """
        names = Activity.list_of_activities()
        persons = Person.list_of_persons()
        dates = Activity.list_of_dates()
        hours = Activity.list_of_hours()
        activities = []
        for i in range(min(20, len(names))):
            name = choice(names)
            names.remove(name)
            date = choice(dates)
            dates.remove(date)
            hour = choice(hours)
            hours.remove(hour)
            person_id = sample(persons,randint(1,10))
            activity_id = randrange(1, 100)
            while activity_id in [x.get_activity_id for x in activities]:
                activity_id = randrange(1, 100)
            description = name
            time = hour
            activities.append(Activity(activity_id, person_id ,date, time, description))
        return activities

    def find_person(self, search):
        """
        Looks for a person with id=person_id and returns one if it exists, None otherwise
        :param search: string
        """
        return self._person_repo.find_person(search)

    def find_activity(self, search):
        """
        Looks for a discipline with id=activity_id and returns one if it exists, None otherwise
        :param search: search
        """
        return self._activity_repo.find_activity(search)

    def add_person(self, person_id, name, phone_number):
        pass
        """
        Adds a person by calling the person repo
        :param person_id: int
        :param name: string
        :param phone_number: string
        :return: None
        """
        person = Person(person_id, name, phone_number)
        if self._person_repo.find_person_by_id(person_id):
            raise UniqueError("Person with id already exists")
        if len(phone_number) != 10:
            raise InvalidPhoneNumber("Invalid phone number")
        self._person_repo.add_person(person)

    def add_activity(self, activity_id, person_id, date, time, description):
        pass
        """
        Adds an activity by calling the activity repo
        :param activity_id: int
        :param person_id: list
        :param date: string
        :param time: string
        :param description: string
        :return: None
        """
        activity = Activity(activity_id, person_id, date, time, description)
        if len(person_id) == 0:
            raise ExistenceError("Cannot have activities with no participants")
        if self._activity_repo.find_activity_by_id(activity_id):
            raise UniqueError("Discipline with id already exists")
        date2 = date.split("/")
        date2[1] = int(date2[1])
        date2[0] = int(date2[0])
        if int(date2[1]) < 1 or int(date2[1]) > 12:
            raise InvalidDate("Invalid date format")
        if int(date2[0]) < 1 or int(date2[0]) > 31:
            raise InvalidDate("Invalid date format")
        if int(date2[1])%2 == 0 and int(date2[0] > 30):
            raise InvalidDate("Invalid date format")
        if int(date2[1]) == 2 and int(date2[1] > 28):
            raise InvalidDate("Invalid date format")
        if self._activity_repo.add_activity(activity):
            raise OverlapError("Activities cannot overlap or invalid ")

    def rem_person(self, person_id):
        """
        Removes the person with id=sid from the person_repo
        :param person_id: integer
        :return: None
        """
        person = self._person_repo.find_person_by_id(person_id)
        if not person:
            raise ExistenceError("No person with given id")
        self._person_repo.rem_person(person)
        name = person.get_name
        self._activity_repo.rem_person(name)

    def rem_activity(self, activity_id):
        """
        Removes the activity with id=activity_id from the activityrepo
        :param activity_id: int
        :return: None
        """
        activity = self._activity_repo.find_activity_by_id(activity_id)
        if not activity:
            raise ExistenceError("No activity with given id")

        self._activity_repo.rem_activity(activity)

    def upd_person_name(self, person_id, name):
        """
        Sets the name of person from person_repo with id=person_id to name
        :param person_id: integer
        :param name: string
        :return: None
        """
        person = self._person_repo.find_person_by_id(person_id)
        if not person:
            raise ExistenceError("No person with given id")
        old_name = person.get_name
        self._person_repo.upd_person_name(person_id, name)
        self._activity_repo.upd_person_name_for_activity(old_name, name)

    def upd_person_phone_number(self, person_id, phone_number):
        """
        Sets the phone number of person from person_repo with id=person_id to phone_number
        :param person_id: integer
        :param phone_number: string
        :return: None
        """
        person = self._person_repo.find_person_by_id(person_id)
        if not person:
            raise ExistenceError("No person with given id")
        self._person_repo.upd_person_phone_number(person_id, phone_number)

    def upd_activity_participants(self, activity_id, person_id):
        """
        Sets the person_id of activity from activity_repo with id=did to person_id
        :param activity_id: integer
        :param person_id: list
        :return: None
        """
        activity = self._activity_repo.find_activity_by_id(activity_id)
        if not activity:
            raise ExistenceError("No activity with given id")
        self._activity_repo.upd_activity_participants(activity_id, person_id)

    def upd_activity_date(self, activity_id, date):
        """
        Sets the date of activity from activity_repo with id=did to date
        :param activity_id: integer
        :param date: string
        :return: None
        """
        date2 = date.split("/")
        date2[0] = int(date2[0])
        date2[1] = int(date2[1])
        date2[2] = int(date2[2])
        if int(date2[1]) < 1 or int(date2[1]) > 12:
            raise InvalidDate("Invalid date format")
        if int(date2[0]) < 1 or int(date2[0]) > 31:
            raise InvalidDate("Invalid date format")
        if int(date2[1]) % 2 == 0 and int(date2[0] > 30):
            raise InvalidDate("Invalid date format")
        if int(date2[1]) == 2 and int(date2[1] > 28):
            raise InvalidDate("Invalid date format")
        activity = self._activity_repo.find_activity_by_id(activity_id)
        if not activity:
            raise ExistenceError("No activity with given id")
        x = self._activity_repo.upd_activity_date(activity_id, date)
        if x:
            return x

    def upd_activity_time(self, activity_id, time):
        """
        Sets the date of activity from activity_repo with id=did to date
        :param activity_id: integer
        :param time: string
        :return: None
        """
        activity = self._activity_repo.find_activity_by_id(activity_id)
        if not activity:
            raise ExistenceError("No activity with given id")
        x = self._activity_repo.upd_activity_time(activity_id, time)
        return x

    def upd_activity_description(self, activity_id, description):
        """
        Sets the description of activity from activity_repo with id=did to description
        :param activity_id: integer
        :param description: string
        :return: None
        """
        activity = self._activity_repo.find_activity_by_id(activity_id)
        if not activity:
            raise ExistenceError("No activity with given id")
        self._activity_repo.upd_activity_description(activity_id, description)

    def lookup_person_by_name(self, search):
        """
        Returns a list of all students who match the name with the parameter name, case insensitive
        :param search: string
        :return: list
        """
        return [person for person in self._person_repo.persons if re.search(search, person.get_name, re.IGNORECASE)]

    def lookup_person_by_phone_number(self, search):
        """
        Returns a list of all students who match the phone number with the parameter phone search
        :param search: string
        :return: list
        """
        return [person for person in self._person_repo.persons if search in person.get_phone_number]

    def lookup_activity_by_date(self, search):
        """
        Returns a list of all activities who match the date with the parameter search
        :param search: string
        :return: list
        """
        return [activity for activity in self._activity_repo.activities if search in activity.get_date]

    def lookup_activity_by_time(self, search):
        """
        Returns a list of all activities who match the time with the parameter search
        :param search: string
        :return: list
        """
        return [activity for activity in self._activity_repo.activities if search in activity.get_time]

    def lookup_activity_by_description(self, search):
        """
        Returns a list of all disciplines who match the description with the parameter search, case insensitive
        :param search: string
        :return: list
        """
        return [activity for activity in self._activity_repo.activities
                if re.search(search, activity.get_description, re.IGNORECASE)]

    def activities_for_given_date(self, date):
        """
        Makes a list of all activities taking place on given date
        :param date: string
        :return: list
        """
        activities = self._activity_repo.activities_for_given_date(date)
        return activities

    def busiest_days(self):
        """
        Computes 2 lists, one with all the dates and one with the free available time in ascending order
        :return: list
        """
        busy_days, free_time = self._activity_repo.busiest_days()
        return busy_days, free_time

    def activities_with_given_person(self, person):
        """
        Makes a list with all activities where a person participates
        :param person: string
        :return: list
        """
        x = self.lookup_person_by_name(person)
        if x is None:
            return None
        activities = self._activity_repo.activities_with_given_person(person)
        return activities
