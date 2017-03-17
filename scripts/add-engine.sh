#!/bin/bash

#curl -XPOST http://127.0.0.1:10002/engine/ -H"Content-Type: application/json" -d '{"name":"nazwa", "address":"localhost"}'
curl -XPOST http://127.0.0.1:10002/engine/ -H"Content-Type: application/json" -d '{}' -v