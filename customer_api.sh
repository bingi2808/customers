#!/bin/bash

BASE_URL="http://localhost:8080/v1/customers"
AUTH="admin:password"

while true; do
    echo "Select an operation:"
    echo "1) Get all customers"
    echo "2) Get customer by ID"
    echo "3) Create a new customer"
    echo "4) Update an existing customer"
    echo "5) Delete a customer"
    echo "6) Exit"

    read -rp "Enter choice [1-6]: " choice

    case $choice in
        1)
            echo "Fetching all customers..."
            echo
            curl -i -u $AUTH "$BASE_URL"
            ;;

        2)
            read -rp "Enter Customer ID: " customerId
            echo "Fetching customer with ID: $customerId"
            echo
            curl -i -u $AUTH "$BASE_URL/$customerId"
            ;;

        3)
            read -rp "Enter First Name: " firstName
            read -rp "Enter Middle Name (or leave blank): " middleName
            read -rp "Enter Last Name: " lastName
            read -rp "Enter Email: " email
            read -rp "Enter Phone: " phone

            customer_data=$(jq -n \
                --arg fn "$firstName" \
                --arg mn "$middleName" \
                --arg ln "$lastName" \
                --arg em "$email" \
                --arg ph "$phone" \
                '{firstName: $fn, middleName: $mn, lastName: $ln, email: $em, phone: $ph}')

            echo "Creating customer..."
            echo
            curl -i -u $AUTH -H "Content-Type: application/json" -X POST -d "$customer_data" "$BASE_URL"
            ;;

        4)
            read -rp "Enter Customer ID to update: " customerId
            read -rp "Enter First Name: " firstName
            read -rp "Enter Middle Name (or leave blank): " middleName
            read -rp "Enter Last Name: " lastName
            read -rp "Enter Email: " email
            read -rp "Enter Phone: " phone

            update_data=$(jq -n \
                --arg fn "$firstName" \
                --arg mn "$middleName" \
                --arg ln "$lastName" \
                --arg em "$email" \
                --arg ph "$phone" \
                '{firstName: $fn, middleName: $mn, lastName: $ln, email: $em, phone: $ph}')

            echo "Updating customer with ID: $customerId"
            echo
            curl -i -u $AUTH -H "Content-Type: application/json" -X PUT -d "$update_data" "$BASE_URL/$customerId"
            ;;

        5)
            read -rp "Enter Customer ID to delete: " customerId
            echo "Deleting customer with ID: $customerId"
            echo
            curl -i -u $AUTH -X DELETE "$BASE_URL/$customerId"
            ;;

        6)
            echo "Exiting script."
            exit 0
            ;;

        *)
            echo "Invalid choice! Please enter a number between 1-6."
            ;;
    esac

    echo
    echo
    read -rp "Do you want to perform another operation? (y/n): " cont
    if [[ "$cont" != "y" ]]; then
        echo "Goodbye!"
        exit 0
    fi
done
