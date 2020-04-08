#!/bin/sh

# Folders:
SECRET_PROVIDER_FOLDER=secret-provider
KEY_FOLDER=keys
SECRETS_FOLDER=secrets
SUB_FOLDERS=src/main/resources/$SECRETS_FOLDER
AUTHORIZATION_SERVER_SECRET_FOLDER=spring-authorization-test-server/$SUB_FOLDERS
RESOURCE_SERVER_FOLDER=resource-test-server/$SUB_FOLDERS

# Filenames:
CONF_FILE=configuration.cnf
PK_FILE=private-key.pem
CERTIFICATE_PK_FILE=certificate.pem
IDENTITY_FILE=identity.p12
KEYSTORE_FILE=keystore.jks
PUBLIC_CERT_FILE=public.txt
SECRETS_PROPERTIES=secrets.properties

# Suffixes:
RESOURCE_SERVER_SUFFIX=resource-server
AUTHORIZATION_SERVER_SUFFIX=authorization-server

# source ./credential-data
# This script use openssl, which can be download from here: https://www.openssl.org/source/
# And java keytool which is part of the jdk.
# First parameter: password of the certificate files and keys (same...)
# Second parameter: key alias which you can found in the key store

move_to_temporary_directory()
{
  echo "Generating temporary folder..."
  mkdir -p $KEY_FOLDER
  cp $CONF_FILE $KEY_FOLDER
  cd $KEY_FOLDER
}

create_keys()
{
  echo "Generating key files..."
  # openssl req -newkey rsa:2048 -x509 -keyout cakey.pem -out cacert.pem -days 3650
  openssl req -new -newkey rsa:2048 -x509 -days 365 -config $CONF_FILE -keyout $PK_FILE -out $CERTIFICATE_PK_FILE -passin pass:"$1"
  # openssl pkcs12 -export -in cacert.pem -inkey cakey.pem -out identity.p12 -name "mykey"
  openssl pkcs12 -export -in $CERTIFICATE_PK_FILE -inkey $PK_FILE -out $IDENTITY_FILE -name "$2" -passin pass: -passout pass:"$1"
  # keytool -importkeystore -srckeystore identity.p12 -srcstoretype pkcs12 -destkeystore keystore.jks
  keytool -importkeystore -srckeystore $IDENTITY_FILE -srcstoretype pkcs12 -destkeystore $KEYSTORE_FILE -srcstorepass "$1" -deststorepass "$1"
  # keytool -list -rfc --keystore keystore.jks | openssl x509 -inform pem -pubkey -noout
  keytool -list -rfc --keystore keystore.jks -storepass "$1" | openssl x509 -inform pem -pubkey -noout > "$PUBLIC_CERT_FILE"
}

create_property_files()
{
  echo "Generating secret property files..."
  {
    printf "security.jwt.public-key=classpath:%s/%s\n" $SECRETS_FOLDER $PUBLIC_CERT_FILE
  } >> "$SECRETS_PROPERTIES.$RESOURCE_SERVER_SUFFIX"
  {
    printf "# Global config:\n"
    printf "security.jwt.keyStorePath=%s/%s\n" $SECRETS_FOLDER $KEYSTORE_FILE
    printf "security.jwt.keyStorePassword=%s\n" "$1"
    printf "# Client key pairs:\n"
    printf "security.jwt.keyPairs.%s=%s\n" "$2" "$1"
  } >> "$SECRETS_PROPERTIES.$AUTHORIZATION_SERVER_SUFFIX"
}

move_secrets_into_destination_folders()
{
  echo "Copying secret files into destination folders..."
  cd "../.."
  cp "$SECRET_PROVIDER_FOLDER/$KEY_FOLDER/$KEYSTORE_FILE" "$AUTHORIZATION_SERVER_SECRET_FOLDER/"
  cp "$SECRET_PROVIDER_FOLDER/$KEY_FOLDER/$PUBLIC_CERT_FILE" "$RESOURCE_SERVER_FOLDER/"
  cp "$SECRET_PROVIDER_FOLDER/$KEY_FOLDER/$KEYSTORE_FILE" "$AUTHORIZATION_SERVER_SECRET_FOLDER/"
  cp "$SECRET_PROVIDER_FOLDER/$KEY_FOLDER/$SECRETS_PROPERTIES.$RESOURCE_SERVER_SUFFIX" "$RESOURCE_SERVER_FOLDER/$SECRETS_PROPERTIES"
  cp "$SECRET_PROVIDER_FOLDER/$KEY_FOLDER/$SECRETS_PROPERTIES.$AUTHORIZATION_SERVER_SUFFIX" "$AUTHORIZATION_SERVER_SECRET_FOLDER/$SECRETS_PROPERTIES"
  echo "Removing temporary files and folder..."
  rm -rf $SECRET_PROVIDER_FOLDER/$KEY_FOLDER
}

if [ $# -eq 2 ]
then
  move_to_temporary_directory
  create_keys "$1" "$2"
  create_property_files "$1" "$2"
  move_secrets_into_destination_folders
else
  exit 1
fi
