# Membership Service

Spring Boot membership service for managing users, membership plans, tiers, tier benefits, subscriptions, membership history, and automatic tier refreshes.

The service uses PostgreSQL for persistence, Spring Data JPA for database access, and Flyway for schema and seed migrations.

## Tech Stack

- Java 17
- Spring Boot 4.0.6
- Spring Web MVC
- Spring Data JPA
- Flyway
- PostgreSQL
- Maven Wrapper
- Docker Compose
- Lombok

## Project Structure

```text
.
├── compose.yaml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── src
    ├── main
    │   ├── java/com/firstclub/membership
    │   │   ├── controllers
    │   │   ├── DTO
    │   │   ├── entity
    │   │   ├── enums
    │   │   ├── exceptions
    │   │   ├── interfaces
    │   │   ├── repository
    │   │   └── services
    │   └── resources
    │       ├── application.yaml
    │       └── db/migration
    └── test
```

## Prerequisites

- JDK 17
- Docker and Docker Compose
- Bash-compatible shell, or use `mvnw.cmd` on Windows

You do not need to install Maven separately because this project includes the Maven Wrapper.

## Database Configuration

The application connects to PostgreSQL using `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5433/membership_db
    username: myuser
    password: secret

  jpa:
    hibernate:
      ddl-auto: validate
    show-sql: true

  flyway:
    enabled: true
    baseline-on-migrate: true
```

The local database is defined in `compose.yaml`:

```yaml
services:
  postgres:
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=membership_db'
      - 'POSTGRES_PASSWORD=secret'
      - 'POSTGRES_USER=myuser'
    ports:
      - '5433:5432'
```

The database is exposed on local port `5433`.

## How To Run

Start PostgreSQL:

```bash
docker compose up -d
```

Run the application:

```bash
./mvnw spring-boot:run
```

On Windows:

```cmd
mvnw.cmd spring-boot:run
```

The service starts on the default Spring Boot port:

```text
http://localhost:8080
```

## Useful Commands

Run tests:

```bash
./mvnw test
```

Build the project:

```bash
./mvnw clean package
```

Run the packaged JAR:

```bash
java -jar target/membership-service-0.0.1-SNAPSHOT.jar
```

Stop PostgreSQL:

```bash
docker compose down
```

Stop PostgreSQL and remove its data volume:

```bash
docker compose down -v
```

Inspect logs:

```bash
docker compose logs -f postgres
```

Connect to the database from Docker:

```bash
docker compose exec postgres psql -U myuser -d membership_db
```

## Flyway Schema And Seed Files

Flyway runs all SQL files in `src/main/resources/db/migration` in version order when the application starts.

### Schema Files

| File | Purpose |
| --- | --- |
| `V1__create_users_table.sql` | Creates `users` table. Stores name, unique email, cohort, and timestamps. |
| `V2__create_user_membership_stats_table.sql` | Creates `user_membership_stats` table. Stores total orders, monthly orders, monthly spend, and last updated timestamp per user. |
| `V3__create_membership_plans_table.sql` | Creates `membership_plans` table. Stores plan name, duration, price, active flag, and creation time. |
| `V4__create_tiers_table.sql` | Creates `tiers` table. Stores tier name, priority, active flag, and creation time. |
| `V5__create_benefits_table.sql` | Creates `benefits` table. Stores benefit name, type, value, active flag, and creation time. |
| `V6__create_tier_benefit_table.sql` | Creates `tier_benefit` join table for many-to-many tier-benefit mapping. |
| `V7__create_tier_criteria_table.sql` | Creates `tier_criteria` table. Stores eligibility rules for each tier. |
| `V8__create_membership_table.sql` | Creates `membership` table. Stores user plan, tier, status, dates, auto-renew flag, and optimistic-lock version. |
| `V9__create_membership_history_table.sql` | Creates `membership_history` table and unique active-membership index. |

### Seed Files

| File | Seed Data |
| --- | --- |
| `V10__seed_membership_plans.sql` | Inserts `MONTHLY`, `QUARTERLY`, and `YEARLY` plans. |
| `V11__seed_tiers.sql` | Inserts `SILVER`, `GOLD`, and `PLATINUM` tiers. |
| `V12__seed_benefits.sql` | Inserts free delivery, discount, priority support, and early access benefits. |
| `V13__seed_tier_benefits.sql` | Maps benefits to tiers. |
| `V14__seed_tier_criteria.sql` | Adds order and spend criteria for Gold and Platinum. |
| `V15__seed_tier_criteria.sql` | Adds cohort criteria for Platinum. |

## Seeded Data

### Membership Plans

| ID | Name | Duration | Price |
| --- | --- | ---: | ---: |
| 1 | `MONTHLY` | 30 days | 99 |
| 2 | `QUARTERLY` | 90 days | 249 |
| 3 | `YEARLY` | 365 days | 899 |

### Tiers

| ID | Name | Priority |
| --- | --- | ---: |
| 1 | `SILVER` | 1 |
| 2 | `GOLD` | 2 |
| 3 | `PLATINUM` | 3 |

Higher priority means a higher tier.

### Benefits

| Name | Type | Value |
| --- | --- | ---: |
| Free Delivery | `FREE_DELIVERY` | 0 |
| Gold Discount | `PERCENTAGE_DISCOUNT` | 10 |
| Platinum Discount | `PERCENTAGE_DISCOUNT` | 15 |
| Priority Support | `PRIORITY_SUPPORT` | 0 |
| Early Access | `EARLY_ACCESS` | 0 |

### Tier Benefits

| Tier | Benefits |
| --- | --- |
| `SILVER` | Free Delivery |
| `GOLD` | Free Delivery, Gold Discount |
| `PLATINUM` | Free Delivery, Platinum Discount, Priority Support, Early Access |

### Tier Criteria

| Tier | Criteria |
| --- | --- |
| `SILVER` | No seeded criteria. This acts as the base tier. |
| `GOLD` | `TOTAL_ORDERS > 10` and `MONTHLY_SPEND > 5000` |
| `PLATINUM` | `TOTAL_ORDERS > 25`, `MONTHLY_SPEND > 15000`, and `COHORT IN STUDENT,EMPLOYEE` |

Note: the current evaluator code compares numeric thresholds using `>=`. The seeded operator column is stored but is not currently used by the evaluator implementations.

## Domain Model

### User

Stored in `users`.

- `id`
- `name`
- `email`
- `cohort`
- `created_at`
- `updated_at`

Allowed cohorts:

- `REGULAR`
- `EMPLOYEE`
- `STUDENT`
- `PARTNER`
- `BETA_TESTER`
- `INFLUENCER`

When a user is created, an empty `user_membership_stats` row is also created.

### User Membership Stats

Stored in `user_membership_stats`.

- `user_id`
- `total_orders`
- `monthly_orders`
- `monthly_spend`
- `last_updated`

These stats are used for tier evaluation.

### Membership Plan

Stored in `membership_plans`.

- `id`
- `name`
- `duration_days`
- `price`
- `active`
- `created_at`

Plans control subscription duration and price.

### Tier

Stored in `tiers`.

- `id`
- `name`
- `priority`
- `active`
- `created_at`

Tier priority controls upgrade and downgrade validation.

### Benefit

Stored in `benefits`.

- `id`
- `name`
- `benefit_type`
- `value`
- `active`
- `created_at`

Benefits are assigned to tiers through the `tier_benefit` join table.

### Tier Criteria

Stored in `tier_criteria`.

- `id`
- `tier_id`
- `criteria_type`
- `operator`
- `threshold_value`
- `created_at`

Supported criteria types:

- `TOTAL_ORDERS`
- `MONTHLY_SPEND`
- `MONTHLY_ORDER`
- `COHORT`

### Membership

Stored in `membership`.

- `id`
- `user_id`
- `plan_id`
- `tier_id`
- `status`
- `start_date`
- `expiry_date`
- `auto_renew`
- `version`
- `created_at`
- `updated_at`

Allowed statuses:

- `ACTIVE`
- `EXPIRED`
- `CANCELLED`
- `PENDING`

Only one active membership is allowed per user. This is enforced by the partial unique index `uq_active_membership_user`.

### Membership History

Stored in `membership_history`.

- `id`
- `membership_id`
- `action`
- `old_tier_id`
- `new_tier_id`
- `remarks`
- `created_at`

Allowed actions:

- `SUBSCRIBED`
- `UPGRADED`
- `DOWNGRADED`
- `CANCELLED`
- `EXPIRED`

## Tier Evaluation

Tier evaluation is handled through the `CriteriaEvaluator` interface and concrete evaluator classes:

- `TotalOrderEvaluator`
- `MonthlySpendEvaluator`
- `MonthlyOrderEvaluator`
- `CohortEvaluator`

`TierEvaluationService` collects evaluator beans into a map keyed by `CriteriaType`.

The evaluation flow is:

1. Load the user.
2. Load the user's membership stats.
3. Load tiers ordered by priority.
4. For each tier, load all criteria for that tier.
5. Evaluate every criterion for that tier.
6. Keep the highest-priority tier for which all criteria pass.

`refreshUserTier` updates the active membership to the eligible tier and writes a `membership_history` row with `UPGRADED` or `DOWNGRADED`.

Important implementation note: `CohortEvaluator` currently does not have a Spring stereotype annotation, so it will not be registered as a bean unless one is added. Because the seeded Platinum criteria includes `COHORT`, tier evaluation for Platinum requires this evaluator to be registered.

## API Endpoints

Base URL:

```text
http://localhost:8080
```

### Users

Get all users:

```bash
curl http://localhost:8080/api/v1/users
```

Create a user:

```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Kartik Agarwal",
    "email": "kartik@example.com",
    "cohort": "STUDENT"
  }'
```

Get a user:

```bash
curl http://localhost:8080/api/v1/users/1
```

Get user's active membership:

```bash
curl http://localhost:8080/api/v1/users/1/memberships
```

Get user's benefits:

```bash
curl http://localhost:8080/api/v1/users/1/benefits
```

Evaluate user's eligible tier:

```bash
curl http://localhost:8080/api/v1/users/1/evaluate-tier
```

Update user membership stats:

```bash
curl -X POST http://localhost:8080/api/v1/users/1/stats \
  -H "Content-Type: application/json" \
  -d '{
    "totalOrders": 30,
    "monthlyOrders": 6,
    "monthlySpend": 20000
  }'
```

### Plans

Get all plans:

```bash
curl http://localhost:8080/api/v1/plans
```

Get a plan:

```bash
curl http://localhost:8080/api/v1/plans/1
```

### Tiers

Get all tiers:

```bash
curl http://localhost:8080/api/v1/tiers
```

Get a tier:

```bash
curl http://localhost:8080/api/v1/tiers/1
```

### Memberships

Subscribe a user:

```bash
curl -X POST http://localhost:8080/api/v1/memberships/subscribe \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "planId": 1,
    "tierId": 1,
    "autoRenew": true
  }'
```

Get membership:

```bash
curl http://localhost:8080/api/v1/memberships/1
```

Get membership history:

```bash
curl http://localhost:8080/api/v1/memberships/1/history
```

Cancel membership:

```bash
curl -X POST http://localhost:8080/api/v1/memberships/1/cancel
```

Upgrade membership:

```bash
curl -X PATCH http://localhost:8080/api/v1/memberships/1/upgrade \
  -H "Content-Type: application/json" \
  -d '{
    "newTierId": 2
  }'
```

Downgrade membership:

```bash
curl -X PATCH http://localhost:8080/api/v1/memberships/1/downgrade \
  -H "Content-Type: application/json" \
  -d '{
    "newTierId": 1
  }'
```

Refresh membership tier automatically:

```bash
curl -X POST http://localhost:8080/api/v1/memberships/1/refresh-tier
```

## Example Local Flow

Start the database and application:

```bash
docker compose up -d
./mvnw spring-boot:run
```

Create a user:

```bash
curl -X POST http://localhost:8080/api/v1/users \
  -H "Content-Type: application/json" \
  -d '{"name":"Kartik Agarwal","email":"kartik@example.com","cohort":"STUDENT"}'
```

Subscribe the user to Silver monthly membership:

```bash
curl -X POST http://localhost:8080/api/v1/memberships/subscribe \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"planId":1,"tierId":1,"autoRenew":true}'
```

Update stats to make the user eligible for a higher tier:

```bash
curl -X POST http://localhost:8080/api/v1/users/1/stats \
  -H "Content-Type: application/json" \
  -d '{"totalOrders":30,"monthlyOrders":6,"monthlySpend":20000}'
```

Evaluate the tier:

```bash
curl http://localhost:8080/api/v1/users/1/evaluate-tier
```

Refresh the membership tier:

```bash
curl -X POST http://localhost:8080/api/v1/memberships/1/refresh-tier
```

Check history:

```bash
curl http://localhost:8080/api/v1/memberships/1/history
```

## Error Handling

The service has a global exception handler for common application errors:

- `ResourceNotFoundException`
- `DuplicateResourceException`
- `InvalidOperationException`
- request validation errors

Errors are returned as structured API error responses.
