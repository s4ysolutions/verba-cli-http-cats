#!/bin/bash

# Test the translation endpoint with authentication

# Configuration
HOST="localhost"
PORT="8080"
SECRET="test-secret-123"

# Test data
SOURCE_TEXT="London is the capital"
SOURCE_LANG="english"
TARGET_LANG="русский"
MODE="explain"
PROVIDER="gemini"

echo "Testing authenticated translation endpoint..."
echo ""
echo "1. Test with valid authentication:"
curl -v -X POST "http://${HOST}:${PORT}/translation" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer ${SECRET}" \
  -d "{
    \"sourceText\": \"${SOURCE_TEXT}\",
    \"sourceLang\": \"${SOURCE_LANG}\",
    \"targetLang\": \"${TARGET_LANG}\",
    \"mode\": \"${MODE}\",
    \"provider\": \"${PROVIDER}\",
    \"quality\": \"optimal\"
  }"

echo -e "\n\n2. Test without authentication (should fail with 403):"
curl -v -X POST "http://${HOST}:${PORT}/translation" \
  -H "Content-Type: application/json" \
  -d "{
    \"sourceText\": \"${SOURCE_TEXT}\",
    \"sourceLang\": \"${SOURCE_LANG}\",
    \"targetLang\": \"${TARGET_LANG}\",
    \"mode\": \"${MODE}\",
    \"provider\": \"${PROVIDER}\",
    \"quality\": \"optimal\"
  }"

echo -e "\n\n3. Test with invalid token (should fail with 403):"
curl -v -X POST "http://${HOST}:${PORT}/translation" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer wrong-token" \
  -d "{
    \"sourceText\": \"${SOURCE_TEXT}\",
    \"sourceLang\": \"${SOURCE_LANG}\",
    \"targetLang\": \"${TARGET_LANG}\",
    \"mode\": \"${MODE}\",
    \"provider\": \"${PROVIDER}\",
    \"quality\": \"optimal\"
  }"

echo -e "\n\nDone!"

