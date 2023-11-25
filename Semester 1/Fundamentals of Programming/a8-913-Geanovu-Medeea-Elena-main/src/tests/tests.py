import unittest

from src.myexceptions import *
from src.services.services import Service
from src.domain.entities import *


# def _print_submenu1():
#     print("\n1. Add a person")
#     print("2. Add an activity")
#     print("3. Remove a person")
#     print("4. Remove an activity")
#     print("5. Update a person")
#     print("6. Update an activity")
#     print("7. List the persons")
#     print("8. List the activities")

class Tests(unittest.TestCase):
    def test_add_person(self):
        service = Service()
        service._person_repo._person_list = []
        service.add_person(1, "P1", "0000000000")
        self.assertEqual(len(service.persons), 1)
        self.assertEqual(service.persons[0].get_person_id, 1)
        self.assertEqual(service.persons[0].get_name, "P1")
        with self.assertRaises(UniqueError):
            service.add_person(1, "S2", "N2")

    def test_add_activity(self):
        service = Service()
        service._activity_repo._activity_list = []
        service.add_activity(1, ["P1"], "04/01/2023", "10:00-11:00", "D1")
        self.assertEqual(len(service.activities), 1)
        self.assertEqual(service.activities[0].get_activity_id, 1)
        self.assertEqual(service.activities[0].get_person_id, ["P1"])
        self.assertEqual(service.activities[0].get_date, "04/01/2023")
        self.assertEqual(service.activities[0].get_time, "10:00-11:00")
        self.assertEqual(service.activities[0].get_description, "D1")
        with self.assertRaises(UniqueError):
            service.add_activity(1, ["P2"], "05/01/2023", "12:00-13:00", "D2")

    def test_rem_person(self):
        service = Service()
        service._person_repo._person_list = []
        service.add_person(1, "P1", "0000000000")
        service.rem_person(1)
        self.assertEqual(len(service.persons), 0)
        with self.assertRaises(ExistenceError):
            service.rem_person(2)

    def test_rem_activity(self):
        service = Service()
        service._activity_repo._activity_list = []
        service.add_activity(1, ["P1"], "04/01/2023", "10:00-11:00", "D1")
        service.rem_activity(1)
        self.assertEqual(len(service.activities), 0)
        with self.assertRaises(ExistenceError):
            service.rem_activity(2)

    def test_upd_person_name(self):
        service = Service()
        service._person_repo._person_list = []
        service.add_person(1, "P1", "0000000000")
        service.upd_person_name(1, "P2")
        self.assertEqual(service.persons[0].get_name, "P2")
        with self.assertRaises(ExistenceError):
            service.upd_person_name(2, "S3")

    def test_upd_person_phone_number(self):
        service = Service()
        service._person_repo._person_list = []
        service.add_person(1, "P1", "0000000000")
        service.upd_person_phone_number(1, "1111111111")
        self.assertEqual(service.persons[0].get_phone_number, "1111111111")
        with self.assertRaises(ExistenceError):
            service.upd_person_name(2, "3333333333")

    def test_upd_activity_participants(self):
        service = Service()
        service._activity_repo._activity_list = []
        service.add_activity(1, ["P1"], "04/01/2023", "10:00-11:00", "D1")
        service.upd_activity_participants(1, ["P2"])
        self.assertEqual(service.activities[0].get_person_id, ["P2"])
        with self.assertRaises(ExistenceError):
            service.upd_activity_participants(2, ["P3"])

    def test_upd_activity_date(self):
        service = Service()
        service._activity_repo._activity_list = []
        service.add_activity(1, ["P1"], "04/01/2023", "10:00-11:00", "D1")
        service.upd_activity_date(1, "11/12/2025")
        self.assertEqual(service.activities[0].get_date, "11/12/2025")
        with self.assertRaises(ExistenceError):
            service.upd_activity_date(2, "01/01/2022")

    def test_upd_activity_time(self):
        service = Service()
        service._activity_repo._activity_list = []
        service.add_activity(1, ["P1"], "04/01/2023", "10:00-11:00", "D1")
        service.upd_activity_time(1, "19:00-23:25")
        self.assertEqual(service.activities[0].get_time, "19:00-23:25")
        with self.assertRaises(ExistenceError):
            service.upd_activity_time(2, "04:00-05:00")

    def test_upb_activity_description(self):
        service = Service()
        service._activity_repo._activity_list = []
        service.add_activity(1, ["P1"], "04/01/2023", "10:00-11:00", "D1")
        service.upd_activity_description(1, "D2")
        self.assertEqual(service.activities[0].get_description, "D2")
        with self.assertRaises(ExistenceError):
            service.upd_activity_description(2, "D3")

    def test_domain_person(self):
        person = Person(1, "P1", "N1")
        self.assertEqual(person.get_person_id, 1)
        self.assertEqual(person.get_name, "P1")
        self.assertEqual(person.get_phone_number, "N1")

    def test_domain_activity(self):
        activity = Activity(1, ["P1"], "01/02/2033", "10:00-11:00", "D1")
        self.assertEqual(activity.get_activity_id, 1)
        self.assertEqual(activity.get_person_id, ["P1"])
        self.assertEqual(activity.get_date, "01/02/2033")
        self.assertEqual(activity.get_time, "10:00-11:00")
        self.assertEqual(activity.get_description, "D1")

    def test_person_repr(self):
        person = Person(1, "P1", "N1")
        self.assertEqual(str(person), "Person: P1 ID: 1 Phone number: N1")

    def test_activity_repr(self):
        activity = Activity(1, ["P1"], "01/01/2022", "10:00-11:00", "D1")
        self.assertEqual(str(activity), "ID: 1 Participants: ['P1'] Date: 01/01/2022 Time: 10:00-11:00 Description: D1")
