#!/bin/sh

if [ $# -eq 2 ]
then
  cd "secret-provider"
  echo "### Creating screts ###"
  ./provide-secrets.sh "$1" "$2"
  KEY_GEN_STATUS=$?
  cd "../"
   if [ $KEY_GEN_STATUS -eq 0 ]; then
    echo "### Key generation was SUCCESSFULL ###"
    ./mvnw clean package
    COMPILE_STATUS=$?
      if [ $COMPILE_STATUS -eq 0 ]; then
        echo "### Compilation was SUCCESSFULL ###"
        echo "### Create docker images ###"
        docker-compose build
        else
        echo "### Compilation FAILED ###"
      fi
    else
    echo "### Key generation FAILED ###"
  fi
else
  echo "You should provide two arguments."
  echo "First: general password for the key generation."
  echo "Second: the key alias which should be contained by the key store."
fi

read -r -p "Press enter to continue"