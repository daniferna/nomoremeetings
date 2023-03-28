docker-down:
	docker compose -f backend/src/main/resources/docker-compose.yml down

docker-up: docker-down
	docker compose -f backend/src/main/resources/docker-compose.yml up -d
