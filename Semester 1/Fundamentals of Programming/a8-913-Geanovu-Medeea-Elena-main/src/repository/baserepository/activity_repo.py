import re
from src.domain.entities import Activity
from src.myexceptions import *


class ActivityRepo:
    def __init__(self, activity_list=None):
        if activity_list is None:
            activity_list = []
        self._activity_list = activity_list

    @property
    def activities(self):
        return self._activity_list

    def find_activity_by_id(self, activity_id):
        """
        iterates through self._activity_list and if an activity with activity_id is found that object is returned,
        :param activity_id: int
        :return:
        """
        #aux = my_filter(self._activity_list, lambda x: x.get_activity_id == activity_id)
        aux = [activity for activity in self._activity_list if activity.get_activity_id == activity_id]
        # aux = [x for x in self._activity_list if x.did == did]
        return None if len(aux) == 0 else aux[0]

    def find_activity(self, search):
        """
        Iterates through self._activity_list and if a discipline with search is found that object is returned,
        otherwise returns None
        :param search: string
        :return: type Discipline if object is found, None otherwise
        """
        #aux = my_filter(self._activity_list, lambda x: search in x.get_date or search in x.get_time or search in x.get_description)
        aux = [activity for activity in self._activity_list if search in activity.get_date or search in activity.get_time or search in activity.get_description]
        # aux = [x for x in self._activity_list if x.did == did]
        return None if len(aux) == 0 else aux

    def add_activity(self, activity):
        """
        Adds an activity of type Activity to self._activity_list
        :param activity: type Activity
        :return: None
        """
        date = activity.get_date
        time = activity.get_time
        t = time.split("-")
        t1 = t[0]
        t2 = t[1]
        if t1 > t2:
            return 1
        t6 = str(t1).split(":")
        t7 = str(t2).split(":")
        t6[0] = int(t6[0])
        t6[1] = int(t6[1])
        t7[0] = int(t7[0])
        t7[1] = int(t7[1])
        if int(t6[0]) > 23 or int(t7[0]) > 23 or int(t6[1]) > 59 or int(t7[1]) > 59:
            return 1
        for x in self._activity_list:
            if x.get_date == date:
                t = x.get_time.split("-")
                t3 = t[0]
                t4 = t[1]
                if (t1 >= t3 and t1 <= t4) or (t1 < t3 and t2 >= t3 and t2 <= t4):
                    return 1
        self._activity_list.append(activity)

    def rem_activity(self, activity):
        """
        Removes the activity
        :param activity: type Activity
        :return: None
        """
        self._activity_list.remove(activity)

    def rem_person(self, name):
        """
        Removes from person_id(list) the person with name
        :param name: string
        :return:
        """
        for i in self._activity_list:
            x = i.get_person_id
            if name in x:
                x.remove(name)
            if not x:
                self.rem_activity(i)

    def upd_person_name_for_activity(self, old_name, name):
        for activity in self._activity_list:
            x = activity.get_person_id
            if old_name in x:
                x.remove(old_name)
                x.append(name)

    def upd_activity_participants(self, activity_id, person_id:list):
        """
        Sets the person_id(participants) of activity with id=activity_id to person_id
        :param activity_id: integer
        :param person_id: list
        :return: None
        """
        activity = self.find_activity_by_id(activity_id)
        activity.set_person_id(person_id)

    def upd_activity_date(self, activity_id, date):
        """
        Sets the date of activity with id=activity_id to date
        :param activity_id: integer
        :param date: string
        :return: None
        """
        activity = self.find_activity_by_id(activity_id)
        time = activity.get_time
        t = time.split("-")
        t1 = t[0]
        t2 = t[1]
        for i in self._activity_list:
            if i.get_date == date:
                t = i.get_time.split("-")
                t3 = t[0]
                t4 = t[1]
                if (t1 >= t3 and t1 <= t4) or (t1 < t3 and t2 >= t3 and t2 <= t4):
                    return 1
        activity.set_date(date)

    def upd_activity_time(self, activity_id, time):
        """
        Sets the date of activity with id=activity_id to time
        :param activity_id: integer
        :param time: string
        :return: None
        """
        activity = self.find_activity_by_id(activity_id)
        date = activity.get_date
        t = time.split("-")
        t1 = t[0]
        t2 = t[1]
        if t1 > t2:
            return 1
        t6 = str(t1).split(":")
        t7 = str(t2).split(":")
        if int(t6[0]) > 23 or int(t7[0]) > 23 or int(t6[1]) > 59 or int(t7[1]) > 59:
            return 1
        for i in self._activity_list:
            if i.get_date == date:
                t = i.get_time.split("-")
                t3 = t[0]
                t4 = t[1]
                if (t1 >= t3 and t1 <= t4) or (t1 < t3 and t2 >= t3 and t2 <= t4):
                    return 1
        activity.set_time(time)

    def upd_activity_description(self, activity_id, description):
        """
        Sets the description of activity with id=activity_id to description
        :param activity_id: integer
        :param description: string
        :return: None
        """
        activity = self.find_activity_by_id(activity_id)
        activity.set_description(description)

    def activities_for_given_date(self, date):
        """
        Iterates through _activity_list and sorts in ascending order by time the objects with date = date
        :param date: string
        :return: list of Activity objects
        """
        #aux = my_filter(self._activity_list, lambda x: date in x.get_date)
        aux = [activity for activity in self._activity_list if date == activity.get_date]
        if len(aux) > 1:
            for i in range(len(aux)-1):
                if aux[i].get_time > aux[i+1].get_time:
                    aux[i],aux[i+1] = aux[i+1],aux[i]
        return aux

    def activities_with_given_person(self, person):
        """
        Iterates through _activity_list and returns a list of all Activity objects including person in person_id
        :param person: string
        :return: list
        """
        # aux = my_filter(self._activity_list, lambda x: person in x.get_person_id)
        # return aux
        return [activity for activity in self._activity_list if re.search(person, str(activity.get_person_id), re.IGNORECASE)]

    def busiest_days(self):
        """
        Iterates through _activity_list and returns a list of Activity objects in ascending order of their free time
        :return: list
        """
        activity_list = [activity for activity in self._activity_list] #all activities
        dates = list(set([activity.get_date for activity in self._activity_list])) #only dates, unique
        times = [1140] * len(dates) #time for each date, index corresponding
        for i in range(len(activity_list)):
            t = activity_list[i].get_time.split("-")
            t1 = str(t[0])
            t2 = str(t[1])
            t1 = t1.split(":")
            t2 = t2.split(":")
            ind = dates.index(activity_list[i].get_date)
            i_time = int(t2[1]) - int(t1[1]) + int(t2[0])*60 - int(t1[0])*60
            times[ind] -= i_time
        for i in range(len(dates)-1):
            for j in range(i,len(dates)):
                if times[i] < times[j]:
                    #print(i,dates[i],i+1,dates[i+1])
                    dates[i], dates[j] = dates[j], dates[i]
                    times[i], times[j] = times[j],times[i]
        return dates,times







# repo = ActivityRepo()
# activity = Activity(1,[1],"1","1","1")
# print(activity)
# repo.add_activity(activity)
# activity = Activity(2,[2],"2","2","2")
# repo.add_activity(activity)
# print("There are " + str(len(repo.activities)) + " activities in total")
# for i in repo.activities:
#     print(i)