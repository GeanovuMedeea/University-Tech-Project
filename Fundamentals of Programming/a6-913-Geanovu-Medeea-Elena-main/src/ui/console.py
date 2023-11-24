from src.domain import operations
from src.domain.entities import get_id
import random

utility_list = ['gas', 'electricity', 'water', 'heating', 'other']

initial_list = [operations.create_apartment(random.randint(1, 1000), random.choice(utility_list), random.randint(1, 1000)),
                operations.create_apartment(random.randint(1, 1000), random.choice(utility_list), random.randint(1, 1000)),
                operations.create_apartment(random.randint(1, 1000), random.choice(utility_list), random.randint(1, 1000)),
                operations.create_apartment(random.randint(1, 1000), random.choice(utility_list), random.randint(1, 1000)),
                operations.create_apartment(random.randint(1, 1000), random.choice(utility_list), random.randint(1, 1000)),
                operations.create_apartment(random.randint(1, 1000), random.choice(utility_list), random.randint(1, 1000)),
                operations.create_apartment(random.randint(1, 1000), random.choice(utility_list), random.randint(1, 1000)),
                operations.create_apartment(random.randint(1, 1000), random.choice(utility_list), random.randint(1, 1000)),
                operations.create_apartment(random.randint(1, 1000), random.choice(utility_list), random.randint(1, 1000)),
                operations.create_apartment(random.randint(1, 1000), random.choice(utility_list), random.randint(1, 1000))]


def read_command():
    '''
    cmd - string contining the operation to be completed
    args - list which hold the arguments needed for the functions
    :return:
    '''
    # return cmd, args
    command = input("Command = ")
    pos = command.find(" ")

    if pos == -1:
        return command, []

    cmd = command[:pos]
    args = command[pos + 1:]
    args = args.split(" ")
    args = [s.strip() for s in args]

    if cmd == 'add':
        return cmd, args

    # 'remove <apartment>', 'remove <start apartment> to <end apartment>', 'remove <type>'
    if cmd == 'remove':
        if len(args) == 1:
            if args[0].isnumeric():
                x = '-1'
                args.append(x)
            else:
                # if id not in ['water', 'heating', 'electricity', 'gas', 'other']:
                #    raise ValueError("an utility from 'water', 'heating', 'electricity', 'gas', 'other' is expected")
                if args[0] not in ['water', 'heating', 'electricity', 'gas', 'other']:
                    print('Type ' + args[0] + ' cannot be deleted since it is not valid')
                    return cmd, []
                else:
                    args.append(args[0])
                    args[0] = '-1'
        elif len(args) == 3:
            del args[1]
            args[1] = str(args[1])

    # 'replace <apartment> <type> with <amount>'
    if cmd == 'replace':
        args.remove(args[2])
        return cmd, args

    # list, list <apartment>, list [ < | = | > ] <amount>
    if cmd == 'list':
        if len(args) == 0:
            return cmd, []
        elif len(args) == 1:
            # if args[0].isnumeric() == False:
            #    raise ValueError("apartment number for printing has to be a positive integer")
            x = '-1'
            args.append(x)
            return cmd, args
        else:
            # if args[0] not in ['=', '<', '>']:
            #    raise ValueError("a comparison operand from '=', '<', or '>' is expected")
            if args[0] in ['=', '<', '>'] and int(args[1]) >= 0:
                return cmd, args
            return cmd, ["invalid_comparison"]

    # filter <type>, filter <value>
    if cmd == 'filter':
        return cmd, args

    if cmd == 'undo':
        pass

    return cmd, args


def add_apartment(all_apartments: list, id, type, amount):
    '''

    :param all_apartments: list of dictioanries, holding all apartment registers
    :param id: the number of the apartment, checked to be positive integer
    :param type: the type of expense, string (water, gas etc)
    :param amount: the expense, checked to be positive integer (300ron, 2 ron etc)
    :return:
    '''
    id = int(id)
    amount = int(amount)

    # check if amount is positive
    # if amount < 0:
    #   raise ValueError("amount for expensese cannot be negative")
    if amount < 0:
        print('Invalid amount')
        return

    # check if expense type is valid
    # if type not in ['water', 'heating', 'electricity', 'gas', 'other']:
    #    raise ValueError("an utility from water, heating, electricity, gas, other is expected")
    if type not in ['water', 'heating', 'electricity', 'gas', 'other']:
        print('Invalid expense type')
        return

    # check for duplicate id
    for d in all_apartments:
        if get_id(d) == id:
            print('Apartment expenses already set for: ', id)
            return

    operations.add_apartment(all_apartments, id, type, amount)


def remove_apartment(all_apartments: list, id, type):
    operations.remove_apartment(all_apartments, id, type)


def replace_apartment(all_apartments: list, id, type, amount):
    operations.replace_apartment(all_apartments, id, type, amount)


def normal_list(all_apartments: list):
    operations.normal_list(all_apartments)


def list_apartment(all_apartments: list, id, type):
    operations.list_apartment(all_apartments, id, type)


def filter_apartment(all_apartments: list, what_to_filter):
    operations.filter_apartment(all_apartments, what_to_filter)


def undo_operation(all_apartments: list, what_happened: list, undo_check):
    operations.undo_operation(all_apartments, what_happened, undo_check)



def print_menu():
    print('Available commands:')
    print('1. Add a new transaction : add <apartment> <type> <amount>')
    print('2. Modify expenses:', 'remove <apartment>', 'remove <start apartment> to <end apartment>', 'remove <type>', 'replace <apartment> <type> with <amount>', sep='\n')
    print('3. Display expenses having different properties:', 'list', 'list <apartment>', 'list [ < | = | > ] <amount>', sep='\n')
    print('4. Filter:', 'filter <type>', 'filter <value>', sep='\n')
    print('5. Undo (we all need ctrl + z in our lives) : undo')
    print('6. Exit?')

def print_commands(commands: dict):
    print(commands)


def run_console():
    all_apartments = initial_list.copy()
    what_happened = [[initial_list.copy()]]
    ok = 0
    undo_check = 0
    commands = {"add": add_apartment, "remove": remove_apartment, "replace": replace_apartment, "list" : list_apartment, "normal_list": normal_list,
                "filter" : filter_apartment, "undo" : undo_operation}
    print_menu()
    while True:
        #print_commands(commands)
        cmd, args = read_command()
        if cmd == "exit":
            break

        #undo_check keeps the program from skipping steps in continuous operation regressing. it resets when a structure modifying operation is done
        if cmd == 'undo':
            if ok == 1:
                operations.undo_operation(all_apartments,what_happened,undo_check)
                undo_check = 1
                continue
            elif ok == 0:
                operations.undo_operation(initial_list, what_happened, undo_check)
                undo_check = 1
                continue


        if cmd == 'add' and ok == 0:
            ok = 1
            what_happened[:] = [[]]


        if ok == 1:
            try:
                commands[cmd](all_apartments, *args)
                operations.save_operation(all_apartments, what_happened, cmd)
            except KeyError as ke:
                print("Option not yet implemented!", ke)
            except:
                if cmd == 'list':
                    if len(args) == 0:
                        commands["normal_list"](all_apartments)
                    elif args[0] == 'invalid_comparison':
                        print('Comparison value/comparison operand is not valid')
        elif ok == 0:
            #the case where we have no items in list, we use implicit one
            try:
                commands[cmd](initial_list, *args)
                operations.save_operation(initial_list, what_happened, cmd)
            except KeyError as ke:
                print("Option not yet implemented!", ke)
                continue
            except:
                if cmd == 'list':
                    if len(args) == 0:
                        commands["normal_list"](initial_list)
                    elif args[0] == 'invalid_comparison':
                        print('Comparison value/comparison operand is not valid')
