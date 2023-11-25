from src.myexceptions import *


class Person:
    def __init__(self, person_id, name, phone_number):
        """
        Creates a person with a given id, name and phone number
        :param person_id: int
        :param name: string
        :param phone_number: string
        :return: none
        """
        self.__person_id = person_id
        self.__name = name
        self.__phone_number = phone_number

    def __eq__(self, other):
        if not isinstance(other, Person):
            return False
        return self.person_id == other.person_id

    def __repr__(self):
        return "Person: " + self.__name + " ID: " + str(self.__person_id) + " Phone number: " + self.__phone_number

    def to_dict(self):
        return {"person_id": self.__person_id, "name": self.__name, "phone_number": self.__phone_number}

    @property
    def get_person_id(self):
        return self.__person_id

    @property
    def get_name(self):
        return self.__name

    @property
    def get_phone_number(self):
        return self.__phone_number

    #@name.setter
    def name(self, new_name):
        self.__name = new_name

    #@person_id.setter
    def person_id(self, new_person_id):
        self.__person_id = new_person_id

    #@phone_number.setter
    def phone_number(self, new_phone_number):
        self.__phone_number = new_phone_number

    @staticmethod
    def list_of_persons():
        try:
            file = open(r"C:\Users\Dell\PycharmProjects\pythonProject\a8-913-Geanovu-Medeea-Elena\src\files\person_names.txt", "r")
            persons = []
            for x in file.readlines():
                persons.append(x[:-1])
            file.close()
            return persons
        except FileNotFoundError:
            return ["Ana", "Alina", "Adela", "Andrei", "Bogdan", "Bianca", "Catalin", "Dan", "Eduard", "Emilia",
                    "Elena", "Flavius", "Fabian", "Georgiana", "Graham", "Laura", "Madalin", "Mara", "Nicolae", "Vlad"]


class Activity:
    def __init__(self, activity_id, person_id, date, time, description):
        """
        Creates an activity with a given id, participant list, date, time and description
        :param activity_id: int
        :param person_id: int
        :param date: string
        :param time: string
        :param description: string
        """
        self.__activity_id = activity_id
        self.__person_id = person_id
        self.__date = date
        self.__time = time
        self.__description = description

    def __eq__(self, other):
        if not isinstance(other, Activity):
            return False
        return self.get_activity_id == other.get_activity_id

    def __repr__(self):
        return "ID: " + str(self.__activity_id) + " Participants: " + str(self.__person_id) + " Date: " + self.__date +\
               " Time: " + self.__time + " Description: " + self.__description

    def to_dict(self):
        return {"activity_id": self.__activity_id, "person_id": self.__person_id, "date": self.__date,
                "time": self.__time, "description": self.__description}

    @property
    def get_activity_id(self):
        return self.__activity_id

    @property
    def get_person_id(self):
        return self.__person_id

    @property
    def get_date(self):
        return self.__date

    @property
    def get_time(self):
        return self.__time

    @property
    def get_description(self):
        return self.__description

    #@activity_id.setter
    def set_activity_id(self, new_activity_id):
        self.__activity_id = new_activity_id

    #@person_id.setter
    def set_person_id(self, new_person_id):
        self.__person_id = new_person_id

    #@date.setter
    def set_date(self, new_date):
        self.__date = new_date

    #@time.setter
    def set_time(self, new_time):
        self.__time = new_time

    #@description.setter
    def set_description(self, new_description):
        self.__description = new_description

    @staticmethod
    def list_of_activities():
        try:
            file = open(r"C:\Users\Dell\PycharmProjects\pythonProject\a8-913-Geanovu-Medeea-Elena\src\files\activity_names.txt", "r")
            activities = []
            for x in file.readlines():
                activities.append(x[:-1])
            file.close()
            return activities
        except FileNotFoundError:
            return ["solve a puzzle", "show and tell", "board games", "escape-room challenge", "cooking class for beginners",
                    "trivia quiz", "painting workshop", "zumba class", "video game night", "horror movie night",
                    "java programming course", "arduino robotics workshop", "vacation to the Maldives", "chess club", "book club",
                    "dungeons and dragons club", "astronomy club", "rock enthusiasts club", "comic book writing club", "python programming course"]

    @staticmethod
    def list_of_dates():
        try:
            file = open(r"C:\Users\Dell\PycharmProjects\pythonProject\a8-913-Geanovu-Medeea-Elena\src\files\dates.txt", "r")
            dates = []
            for x in file.readlines():
                dates.append(x[:-1])
            file.close()
            return dates
        except FileNotFoundError:
            return ["17/12/2022", "04/01/2023", "18/02/2023", "19/02/2023", "20/02/2023",
                    "28/02/2023", "17/12/2022", "04/01/2023", "22/04/2023", "29/04/2023",
                    "04/01/2023", "31/05/2023", "11/06/2023", "17/12/2022", "17/12/2022",
                    "17/12/2022", "29/04/2023", "05/10/2023", "04/11/2023", "29/04/2023"]

    @staticmethod
    def list_of_hours():
        try:
            file = open(r"C:\Users\Dell\PycharmProjects\pythonProject\a8-913-Geanovu-Medeea-Elena\src\files\hours.txt", "r")
            hours = []
            for x in file.readlines():
                hours.append(x[:-1])
            file.close()
            return hours
        except FileNotFoundError:
            return ["4:00-4:50", "05:00-5:45", "06:00-6:50", "07:00-7:50", "08:10-8:45",
                    "09:00-9:30", "09:35-10:15", "10:20-11:00", "11:10-11:50", "12:00-13:00",
                    "13:10-14:00", "14:15-15:45", "16:00-16:55", "17:20-18:30", "18:35-19:30",
                    "19:35-20:15", "20:30-21:00", "21:05-22:00", "22:10-22:50", "23:00-23:30"]
