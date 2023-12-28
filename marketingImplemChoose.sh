#!/bin/bash

# Vérifier le nombre d'arguments
if [ "$#" -ne 1 ]; then
    echo "Usage: $0 -c <service_number>"
    # Pause avant de fermer le terminal
    read -p "Appuyez sur une touche pour fermer le script..."
    exit 1
fi

# Récupérer l'argument
while getopts "c:" opt; do
    case $opt in
        c)
            service_number=$OPTARG
            ;;
        \?)
            echo "Usage: $0 -c <service_number>"
            # Pause avant de fermer le terminal
            read -p "Appuyez sur une touche pour fermer le script..."
            exit 1
            ;;
    esac
done

# Vérifier la valeur de service_number
if [ "$service_number" != "1" ] && [ "$service_number" != "2" ]; then
    echo "Service number should be 1 or 2"
    # Pause avant de fermer le terminal
    read -p "Appuyez sur une touche pour fermer le script..."
    exit 1
fi

# Mettre à l'échelle les services en conséquence
if [ "$service_number" == "1" ]; then
    echo "Scaling marketing to 1 and marketing-decision-tree to 0"
    docker-compose up -d --scale marketing=1 --scale marketing-decision-tree=0
elif [ "$service_number" == "2" ]; then
    echo "Scaling marketing to 0 and marketing-decision-tree to 1"
    docker-compose up -d --scale marketing=0 --scale marketing-decision-tree=1
fi

# Pause avant de fermer le terminal
if [ "$OSTYPE" == "msys" ]; then
    read -p "Appuyez sur Entrée pour fermer le script..."
fi
