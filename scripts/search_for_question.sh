#!/usr/bin/env bash
dir=$(dirname "$0")

curl \
    --header "Content-Type: application/json" \
    --request GET \
    --silent \
    --location "http://localhost:8080/questions?term=Kotlin" | jq .
