from src.domain.entities import Person


class PersonRepo:
    def __init__(self, person_list=None):
        if person_list is None:
            person_list = []
        self._person_list = person_list

    @property
    def persons(self):
        return self._person_list

    def find_person(self, search):
        """
        Iterates through self.person_list and if a person with id=person_id is found that object is returned,
        otherwise returns None
        :param search: string
        :return: type Person if object is found, None otherwise
        """
        #aux = my_filter(self._person_list, lambda x: x.get_phone_number == search or x.get_name == search)
        aux = [person for person in self._person_list if person.get_phone_number ==search or person.get_name == search]
        return None if len(aux) == 0 else aux[0]

    def add_person(self, person):
        """
        Adds a person to self._person_list
        :param person: type Person
        :return: None
        """
        self._person_list.append(person)

    def rem_person(self, person):
        """
        Removes the student
        :param student: type Student
        :return: None
        """
        self._person_list.remove(person)

    def find_person_by_id(self, person_id):
        """
        iterates through self._person_list and if a person with person_id is found that object is returned
        :param person_id: int
        :return:
        """
        #aux = my_filter(self._person_list, lambda x: x.get_person_id == person_id)
        aux = [person for person in self._person_list if person.get_person_id == person_id]
        return None if len(aux) == 0 else aux[0]

    def upd_person_name(self, person_id, name):
        """
        Sets the name of person with id=person_id to name
        :param person_id: integer
        :param name: string
        :return: None
        """
        person = self.find_person_by_id(person_id)
        person.name(name)

    def upd_person_phone_number(self, person_id, phone_number):
        """
        Sets the phone_number of person with id=person_id to phone_number
        :param person_id: integer
        :param phone_number: string
        :return: None
        """
        person = self.find_person_by_id(person_id)
        person.phone_number(phone_number)

# person =Person(1,"1","1")
# repo = PersonRepo()
# repo.add_person(person)
# person = Person(2,"2","2")
# repo.add_person(person)
# print("There are " + str(len(repo.persons)) + " persons in total.")
# for i in repo.persons:
#     print(i)