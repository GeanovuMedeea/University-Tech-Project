import copy
from src.domain.entities import create_apartment
from src.domain.entities import get_id, get_type, get_amount, set_amount, set_type


def add_apartment(all_apartment: list, id, type, amount):
    '''
    :param all_apartment: list of dictionaries
    :param id: positive integer
    :param type: string of characters
    :param amount: positive integer
    :return:
    '''
    apartment = create_apartment(id, type, amount)
    all_apartment.append(apartment)
    print('Added successfully')


def remove_apartment(all_apartments: list, id, type):
    '''
    :param all_apartments: list of dictionaries
    :param id: positive integer
    :param type: either positive integer or string of characters
    :return:
    '''
    check = 0
    #remove apartments with a utility type
    if type in ['water', 'heating', 'electricity', 'gas', 'other']:
        type_to_delete = type
        for d in all_apartments:
            if get_type(d) == type:
                check = 1
        if check == 0:
            print('The expenses type ' + type + ' does not exist!')
            return
        all_apartments[:] = [d for d in all_apartments if (get_type(d) != type_to_delete)]
        print('Expenses of type ' + type + ' deleted successfully')
    elif type > '-1': #remove apartments from start to finish numbers
        start = int(id)
        end = int(type)
        for d in all_apartments:
            if get_id(d) >= start and get_id(d) <= end:
                check = 1
        if check == 0:
            print('The expenses for apartments between', start, 'and', end, 'do not exist!')
            return
        all_apartments[:] = [d for d in all_apartments if (get_id(d) < start or get_id(d) > end)]
        print('Expenses for apartments ' + id + ' to ' + type + ' deleted successfully')
    else: #remove apartment with a certain id number
        key_to_delete = int(id)
        for d in all_apartments:
            if get_id(d) == key_to_delete:
                check = 1
        if check == 0:
            print('The expenses for apartment ' + id + ' do not exist!')
            return
        all_apartments[:] = [d for d in all_apartments if get_id(d) != key_to_delete]
        print('Expense for apartment ' + id + ' deleted successfully')


def replace_apartment(all_apartments: list, id, type, amount):
    '''
    :param all_apartments: list of dictionaries
    :param id: positive integer
    :param type: string of characters
    :param amount: positive integer
    :return:
    '''
    # if int(id) < 1:
    #    raise ValueError("apartment number should be a positive integer")
    # if type not in ['water', 'heating', 'electricity', 'gas', 'other']:
    #    raise ValueError("an utility from water, heating, electricity, gas, other is expected")
    # if int(amount) < 1:
    #    raise ValueError("expenses for utilities should be a positive integer")
    for d in all_apartments:
        if get_id(d) == int(id):
            set_type(d, type)
            set_amount(d, amount)
            print('Expense modified successfully')
            return
    print('The expense does not exist/invalid apartment number or type')


def normal_list(all_apartments: list):
    '''
    :param all_apartments: list of dictionaries
    :return:
    '''
    print(all_apartments)


def list_apartment(all_apartments: list, id, limit):
    '''
    :param all_apartments: list of dictionaries
    :param id: positive integer
    :param limit: positive integer
    :return:
    '''
    # if int(id) < 1:
    #    raise ValueError("apartment number should be a positive integer")
    # if int(limit) < 1:
    #    raise ValueError("the amount to print expenses for should be a positive integer")
    #print apartment with an id number
    if limit == '-1':
        for d in all_apartments:
            if get_id(d) == int(id):
                print('The apartment expenses: ', d)
                return
        print('Apartment id does not exist')
    else:
        print('The apartments with expenses ' + id + ' than/with ' + limit + ' are:')
        if id == '=': #print apartments with expenses equal to limit
            for d in all_apartments:
                if int(get_amount(d)) == int(limit):
                    print(d)
        elif id == '<': #print apartments with expenses smaller than limit
            for d in all_apartments:
                if int(get_amount(d)) < int(limit):
                    print(d)
        else:
            for d in all_apartments: #print apartments with expenses greater than limit
                if int(get_amount(d)) > int(limit):
                    print(d)


def filter_apartment(all_apartments: list, what_to_filter):
    '''
    :param all_apartments: list of dictionaries
    :param what_to_filter: positive integer or string of characters
    :return:
    '''
    # if what_to_filter not in ['water', 'heating', 'electricity', 'gas', 'other']:
    #    raise ValueError("cannot filter something that is not an utility from water, heating, electricity, gas, other is expected")

    #keep apartments with a certain type
    if what_to_filter in ['water', 'heating', 'electricity', 'gas', 'other']:
        all_apartments[:] = [d for d in all_apartments if (get_type(d) == what_to_filter)]
        print('Filter successful')
    else:
        try:
            if int(what_to_filter) > 0: #keep apartments with expenses less than a number
                all_apartments[:] = [d for d in all_apartments if (int(get_amount(d)) < int(what_to_filter))]
                print('Filter successful')
                return
            print('Invalid amount')
        except:
            print('Invalid type')
            return


def undo_operation(all_apartments: list, what_happened: list, undo_check):
    '''
    :param all_apartments: list of characters
    :param what_happened: list of lists holding the previous iterations of the list all_apartments
    :param undo_check: 0 or 1, shows if there are continuous undo operations
    :return:
    '''
    try:
        if undo_check == 0:
            what_happened.pop()
        #print(what_happened)
        all_apartments[:] = copy.deepcopy(what_happened.pop())
        #print(all_apartments)
        print('Undo successful')
    except:
        print('No operations to undo')


def save_operation(all_apartments: list, what_happened: list, type):
    '''
    :param all_apartments: list of dictionaries
    :param what_happened: list of lists (containing dictionaries previously contained in all_apartments)
    :param type: string of characters, skips implicitly list operations since they don't modify all_apartments
    :return:
    '''
    if type != 'list':
        x = copy.deepcopy(all_apartments)
        if len(x) > 0:
            #what_happened.append(x)
            what_happened.append(x)
