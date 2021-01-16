from csv import DictReader
from json import dumps
from functools import reduce, partial
from operator import getitem

def parse(president):
    name_parts = president["name"].split()
    alive = "born" in president["dateOfBirth"]
    dates_parts = president["dateOfBirth"].split("\u2013") if not alive else president["dateOfBirth"].split("(born ")
    return {
        "first_name": name_parts[0],
        "middle_name": name_parts[1] if len(name_parts) == 3 else None,
        "last_name": name_parts[2] if len(name_parts) == 3 else name_parts[1],
        "date_of_birth": int(dates_parts[0][1:]) if not alive else int(dates_parts[1][:-1]),
        "date_of_death": int(dates_parts[1][:-1]) if not alive else None
    }

def reducer_first_name(acc, base_info):
    first_name = base_info["first_name"]
    if first_name not in acc:
        acc[first_name] = {"date_of_birth": [], "lifetime": []}

    current = acc[first_name]

    acc[first_name] = {
        "date_of_birth": current["date_of_birth"] + [base_info["date_of_birth"]],
        "lifetime": current["lifetime"] + [base_info["date_of_death"] - base_info["date_of_birth"]] if base_info["date_of_death"] else current["lifetime"]
    }
    return acc

def average(president):
    date_of_birth = president[1]["date_of_birth"]
    lifetime = president[1]["lifetime"]
    return {
        "first_name": president[0],
        "date_of_birth": int(sum(date_of_birth)/len(date_of_birth)),
        "lifetime": int(sum(lifetime) / len(lifetime)) if len(lifetime) != 0 else None
    }

with open("./presidents_names.csv", 'r') as csvfile:
    reader = DictReader(csvfile)
    base_info = [parse(president) for president in reader]
    president_by_date = reduce(reducer_first_name, base_info, dict())
    first_name_by_date = list(map(average, president_by_date.items()))
    print(dumps({
        "first_name": first_name_by_date,
        "last_names": list(map(lambda president: president["last_name"], base_info)),
        "middle_name": list(map(lambda president: president["middle_name"], base_info))
    }, indent=2))
