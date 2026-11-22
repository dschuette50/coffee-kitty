# 002. Store cash deposits and balances in cents

## Decision Metadata

### Status

Accepted

### Date

2026-11-22

### Involved

Project requester

## Context

A cash deposit increases a user's personal balance and the shared kitty cash balance. Concurrent deposits must
preserve every credit and its deposit record.

## Considered Options

- Store balances and record each deposit in the same transaction.
- Calculate balances from the history of monetary transactions. This would need a broader model for opening balances
  and other monetary activity.

## Decision

Store the personal balance on the user, the cash balance in one singleton row, and deposits in a separate table.
Store amounts as integer euro cents. Take pessimistic write locks in recipient-then-kitty order and commit both
credits and the deposit together. Initialize the singleton on first use with an insert that tolerates a conflict
on its primary key.

## Consequences

The same transaction creates the opening cash row and applies the first deposit. The shared cash row serializes
cash updates across recipients. Negative personal balances represent debt.
