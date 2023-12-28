valid_countries = [
    "France", "United States", "Canada", "Germany", "United Kingdom", "Australia", "Japan", "China", "Brazil", "India",
    "South Africa", "Mexico", "Spain", "Italy", "Russia", "Sweden", "Switzerland", "Netherlands", "Norway", "New Zealand",
    "Argentina", "Austria", "Belgium", "Chile", "Colombia", "Denmark", "Egypt", "Finland", "Greece", "Hong Kong",
    "Ireland", "Israel", "South Korea", "Malaysia", "Singapore", "Portugal", "Poland", "Thailand", "Turkey", "Ukraine",
    "Vietnam", "Czech Republic", "Hungary", "Romania", "Croatia", "Bulgaria", "Slovakia", "Slovenia", "Estonia", "Latvia",
    "Lithuania", "Iceland", "Luxembourg", "Liechtenstein", "Monaco", "Malta", "Cyprus", "Azerbaijan", "Kazakhstan", "Uzbekistan",
    "Tunisia", "Morocco", "Nigeria", "Kenya", "South Sudan", "Ghana", "Ethiopia", "Uganda", "Rwanda",
]

def validate_country(country):
    return country in valid_countries