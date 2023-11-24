from src.services.services import Service
from src.myexceptions import *


class UI:
    def __init__(self, service=None):
        if service is None:
            service = Service()
        self._service = service

    def list_persons(self,persons):
        print("There are " + str(len(self._service.persons)) + " persons in total.")
        for i in persons:
            print(i)

    def list_activities(self,activities):
        print("There are " + str(len(self._service.activities)) + " activities in total")
        for i in activities:
            print(i)

    def ui_add_person(self):
        try:
            person_id = int(input("Input the id of your new person: ").strip())
            name = input("Input the name of your new person: ").strip()
            phone_number = input("Input the phone number of your new person: ").strip()
            self._service.add_person(person_id, name, phone_number)
            print("Person successfully added!")
        except Exception as e:
            print(e)

    def ui_add_activity(self):
        try:
            activity_id = int(input("Input the id of your new activity: ").strip())
            n = int(input("How many participants: "))
            person_id = []
            print("Input the participants: ")
            for i in range(n):
                x = input()
                person_id.append(x)
            date = input("Input the date of your new activity: ").strip()
            time = input("Input the time of your new activity: ").strip()
            description = input("Input the description of your new activity: ").strip()
            self._service.add_activity(activity_id, person_id, date, time, description)
            print("Activity successfully added!")
        except Exception as e:
            print(e)

    def ui_rem_person(self):
        try:
            person_id = int(input("Input the id of the person you want to remove: ").strip())
            self._service.rem_person(person_id)
            print("Person successfully removed!")
        except Exception as e:
            print(e)

    def ui_rem_activity(self):
        try:
            activity_id = int(input("Input the id of the activity you want to remove: ").strip())
            self._service.rem_activity(activity_id)
            print("Activity successfully removed!")
        except Exception as e:
            print(e)

    def ui_upd_person_name(self):
        try:
            person_id = int(input("Input the id of the person you want to update: ").strip())
            name = input("Input the new name: ").strip()
            self._service.upd_person_name(person_id, name)
            print("Person successfully updated!")
        except Exception as e:
            print(e)

    def ui_upd_person_phone_number(self):
        try:
            person_id = int(input("Input the id of the person you want to update: ").strip())
            phone_number = input("Input the new phone number: ").strip()
            self._service.upd_person_phone_number(person_id, phone_number)
            print("Person successfully updated!")
        except Exception as e:
            print(e)

    def ui_upd_activity_participants(self):
        try:
            activity_id = int(input("Input the id of the activity you want to update: ").strip())
            n = int(input("How many participants?").strip())
            person_id = []
            for i in range(n):
                person = input().strip()
                x = self._service.lookup_person_by_name(person)
                if not x:
                    raise ExistenceError("Person does not exist in planner")
                person_id.append(person)
            self._service.upd_activity_participants(activity_id, person_id)
            print("Activity successfully updated!")
        except Exception as e:
            print(e)

    def ui_upd_activity_date(self):
        try:
            activity_id = int(input("Input the id of the activity you want to update: ").strip())
            date = input("Input the new date: ").strip()
            if len(date) != 10:
                raise ValueError("Invalid date format")
            x = self._service.upd_activity_date(activity_id, date)
            if x:
                raise OverlapError("Activity time overlaps")
            print("Activity successfully updated!")
        except Exception as e:
            print(e)

    def ui_upd_activity_time(self):
        try:
            activity_id = int(input("Input the id of the activity you want to update: ").strip())
            time = input("Input the new time: ").strip()
            if len(time) != 11:
                raise InvalidTime("Invalid time format")
            t = time.split("-")
            if t[1] < t[0]:
                raise InvalidTime("Invalid time format")
            x = self._service.upd_activity_time(activity_id, time)
            if x:
                raise OverlapError("Activity time overlap")
            print("Activity successfully updated!")
        except Exception as e:
            print(e)

    def ui_upd_activity_description(self):
        try:
            activity_id = int(input("Input the id of the activity you want to update: ").strip())
            description = (input("Input the new description: ")).strip().lower()
            self._service.upd_activity_description(activity_id, description)
            print("Discipline successfully updated!")
        except Exception as e:
            print(e)

    def ui_lookup_person_by_name(self):
        search = input("Input the name of the person you wish to look up: ").strip()
        try:
            self.list_persons(self._service.lookup_person_by_name(search))
            if not self._service.lookup_person_by_name(search):
                raise ExistenceError("Person with given name doesn't exist in planner")
        except Exception as e:
            print(e)

    def ui_lookup_person_by_phone_number(self):
        search = input("Input the phone number of the person you wish to look up: ").strip()
        try:
            self.list_persons(self._service.lookup_person_by_phone_number(search))
            if not self._service.lookup_person_by_phone_number(search):
                raise ExistenceError("Person with given phone number doesn't exist in planner")
        except Exception as e:
            print(e)

    def ui_lookup_activity_by_date(self):
        try:
            search = input("Input the date of the activity you wish to look up: ").strip()
            self.list_activities(self._service.lookup_activity_by_date(search))
            if not self._service.lookup_activity_by_date(search):
                raise ExistenceError("There are no activities on given date")
        except Exception as e:
            print(e)

    def ui_lookup_activity_by_time(self):
        search = input("Input the time of the activity you wish to look up: ").strip()
        try:
            self.list_activities(self._service.lookup_activity_by_time(search))
            if not self._service.lookup_activity_by_time(search):
                raise ExistenceError("Activities with given time don't exist in planner")
        except Exception as e:
            print(e)

    def ui_lookup_activity_by_description(self):
        search = input("Input the description of the activity you wish to look up: ").strip()
        try:
            self.list_activities(self._service.lookup_activity_by_description(search))
            if not self._service.lookup_activity_by_description(search):
                raise ExistenceError("Activities with given description don't exist in planner")
        except Exception as e:
            print(e)

    def ui_find_person_by_id(self):
        try:
            person_id = int(input("Input the id of the person you wish to find: ").strip())
            person = self._service.find_student_by_id(person_id)
            if not person:
                print("No person with such id")
            else:
                print(person)
        except Exception as e:
            print(e)

    def ui_find_activity_by_id(self):
        try:
            activity_id = int(input("Input the id of the activity you wish to find: ").strip())
            activity = self._service.find_activity_by_id(activity_id)
            if not activity:
                print("No activity with such id")
            else:
                print(activity)
        except Exception as e:
            print(e)

    def ui_manage_lists(self):
        self._print_submenu1()
        choice2 = input("> ").strip()
        if choice2 == "1":
            self.ui_add_person()
        elif choice2 == "2":
            self.ui_add_activity()
        elif choice2 == "3":
            self.ui_rem_person()
        elif choice2 == "4":
            self.ui_rem_activity()
        elif choice2 == "5":
            self._print_submenu2()
            choice3 = input(">").strip()
            if choice3 == "1":
                self.ui_upd_person_name()
            elif choice3 == "2":
                self.ui_upd_person_phone_number()
            elif choice3 == "0":
                pass
            else:
                print("Invalid choice")
        elif choice2 == "6":
            self._print_submenu3()
            choice3 = input(">").strip()
            if choice3 == "1":
                self.ui_upd_activity_participants()
            elif choice3 == "2":
                self.ui_upd_activity_date()
            elif choice3 == "3":
                self.ui_upd_activity_time()
            elif choice3 == "4":
                self.ui_upd_activity_description()
            elif choice3 == "0":
                pass
            else:
                print("Invalid choice")
        elif choice2 == "7":
            self.list_persons(self._service.persons)
        elif choice2 == "8":
            self.list_activities(self._service.activities)
        elif choice2 == "0":
            pass
        else:
            print("Invalid choice")

    def ui_statistics(self):
        self._print_submenu_statistics()
        choice_s = input("> ").strip()
        if choice_s == "1":
            date = input("Input the date: ").strip()
            s = self._service.activities_for_given_date(date)
            try:
                for x in s:
                    print(x)
                if not s:
                    raise ExistenceError("There are no activities for the given date: " + date)
            except Exception as e:
                print(e)
        elif choice_s == "2":
            try:
                days,times = self._service.busiest_days()
                for i in range(len(days)):
                    print(days[i], times[i]//60,"hours and", times[i]%60, "minutes free")
                if not self._service.busiest_days():
                    raise ExistenceError("No activities")
            except Exception as e:
                print(e)
        elif choice_s == "3":
            person = input("Input the name of the person: ").strip()
            s = self._service.activities_with_given_person(person)
            try:
                for x in s:
                    print(x)
                if not s:
                    raise ExistenceError("There are no activities with the given person: " + person)
            except Exception as e:
                print(e)
        elif choice_s == "0":
            pass
        else:
            print("Invalid choice")

    @staticmethod
    def _print_submenu1():
        print("\n1. Add a person")
        print("2. Add an activity")
        print("3. Remove a person")
        print("4. Remove an activity")
        print("5. Update a person")
        print("6. Update an activity")
        print("7. List the persons")
        print("8. List the activities")
        print("0. Abandon\n")

    @staticmethod
    def _print_submenu2():
        print("\n1. Update person name")
        print("2. Update person phone number")
        print("0. Abandon\n")

    @staticmethod
    def _print_submenu3():
        print("\n1. Update activity participants")
        print("2. Update activity date")
        print("3. Update activity time")
        print("4. Update activity description\n")

    @staticmethod
    def _print_submenu_statistics():
        print("\n1. Activities for a given date")
        print("2. Busiest days")
        print("3. Activities with a given person")
        print("0. Abandon\n")

    @staticmethod
    def _print_menu():
        print("\n1. Manage the list of persons and available activities")
        print("2. Search for a person by name")
        print("3. Search for a person by phone number")
        print("4. Search for an activity by date")
        print("5. Search for an activity by time")
        print("6. Search for an activity by description")
        print("7. Create statistics")
        print("0. Exit the program\n")

    def start(self):
        print("Welcome to my activity planner app!")
        while True:
            self._print_menu()
            choice = input("> ").strip()
            if choice == "1":
                self.ui_manage_lists()
            elif choice == "2":
                self.ui_lookup_person_by_name()
            elif choice == "3":
                self.ui_lookup_person_by_phone_number()
            elif choice == "4":
                self.ui_lookup_activity_by_date()
            elif choice == "5":
                self.ui_lookup_activity_by_time()
            elif choice == "6":
                self.ui_lookup_activity_by_description()
            elif choice == "7":
                self.ui_statistics()
            elif choice == "0":
                print("Exit program!")
                break
            else:
                print("Invalid choice")

