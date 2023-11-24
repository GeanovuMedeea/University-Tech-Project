from random import randrange
from datetime import timedelta


l = []
dates = []
hours = []


def random_date(start, end):
    """
    This function will return a random datetime between two datetime
    objects.
    """
    delta = end - start
    int_delta = (delta.days * 24 * 60 * 60) + delta.seconds
    random_second = randrange(int_delta)
    return start + timedelta(minutes=random_second/60)



def my_filter(iterable, accept):
    new_list = type(iterable)()
    for x in iterable:
        if accept(x):
            new_list.append(x)
    return new_list
