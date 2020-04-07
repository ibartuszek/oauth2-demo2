#!/bin/sh

# Folders:
SECRET_FOLDER=secret-provider
KEY_FOLDER=keys
SUB_FOLDERS=src/main/resources/secrets
AUTHORIZATION_SERVER_FOLDER=spring-authorization-test-server/$SUB_FOLDERS
RESOURCE_SERVER_FOLDER=resource-test-server/$SUB_FOLDERS

# Filenames:
CONF_FILE=configuration.cnf
PK_FILE=private-key.pem
CERTIFICATE_PK_FILE=certificate.pem
IDENTITY_FILE=identity.p12
KEYSTORE_FILE=keystore.jks
PUBLIC_CERT_FILE=public.txt

# source ./credential-data
# This script use openssl, which can be download from here: https://www.openssl.org/source/
# And java keytool which is part of the jdk.
# First parameter: password of the certificate files and keys (same...)
# Second parameter: key alias which you can found in the key store

create_keys()
{
  mkdir -p $KEY_FOLDER
  cp $CONF_FILE $KEY_FOLDER
  cd $KEY_FOLDER
  # openssl req -newkey rsa:2048 -x509 -keyout cakey.pem -out cacert.pem -days 3650
  openssl req -new -newkey rsa:2048 -x509 -days 365 -config $CONF_FILE -keyout $PK_FILE -out $CERTIFICATE_PK_FILE -passin pass:"$1"
  # openssl pkcs12 -export -in cacert.pem -inkey cakey.pem -out identity.p12 -name "mykey"
  openssl pkcs12 -export -in $CERTIFICATE_PK_FILE -inkey $PK_FILE -out $IDENTITY_FILE -name "$2" -passin pass: -passout pass:"$1"
  # keytool -importkeystore -srckeystore identity.p12 -srcstoretype pkcs12 -destkeystore keystore.jks
  keytool -importkeystore -srckeystore $IDENTITY_FILE -srcstoretype pkcs12 -destkeystore $KEYSTORE_FILE -srcstorepass "$1" -deststorepass "$1"
  # keytool -list -rfc --keystore keystore.jks | openssl x509 -inform pem -pubkey -noout
  keytool -list -rfc --keystore keystore.jks -storepass "$1" | openssl x509 -inform pem -pubkey -noout > "$PUBLIC_CERT_FILE"
  cd "../.."
  cp "$SECRET_FOLDER/$KEY_FOLDER/$KEYSTORE_FILE" "$AUTHORIZATION_SERVER_FOLDER/"
  cp "$SECRET_FOLDER/$KEY_FOLDER/$PUBLIC_CERT_FILE" "$RESOURCE_SERVER_FOLDER/"
  rm -rf $SECRET_FOLDER/$KEY_FOLDER
}

if [ $# -eq 2 ]
then
  create_keys "$1" "$2"
else
  echo "You should provide two arguments."
  echo "First: general password for the key generation."
  echo "Second: the key alias which should be contained by the key store."
fi

read -r -p "Press enter to continue"

