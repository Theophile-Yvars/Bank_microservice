from utils.countryValidator import validate_country

def checkArgsValidity(args) :
    if args.country is None :
        print("Country is null")
        exit(1)
    if args.number is None :
        print("Amount is null")
        exit(1)
    if not validate_country(args.country) :
        print("Country not valid")
        exit(1)
    if int(args.number) <= 0 :
        print("Amount null or negative")
        exit(1)