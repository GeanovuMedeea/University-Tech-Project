def create_apartment(id, type, amount):
    '''
    :param id: positive integer
    :param type: string of characters
    :param amount: positive integer
    :return:
    '''
    return {"id": id, "type": type, "amount": amount}


def get_id(apartment):
    return apartment["id"]


def set_idapartment(apartment, id):
    apartment["id"] = id


def get_type(apartment):
    return apartment["type"]


def set_type(apartment, type):
    apartment["type"] = type


def get_amount(apartment):
    return apartment["amount"]


def set_amount(apartment, amount):
    apartment["amount"] = amount