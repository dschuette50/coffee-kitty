# FR-6: Confirm Expense Entry

## Statement

Before recording a user-entered expense, Coffee Kitty MUST require the user to confirm the entered expense details.

## Rationale

Members may accidentally enter incorrect expense amounts or details. A confirmation step gives them an opportunity to identify mistakes before an expense affects their personal balance, without requiring prior administrator approval.

## Success Criteria

- An expense awaiting confirmation does not change the user's personal balance.
- The user can review the expense amount and other entered details before confirming.
- After confirmation, Coffee Kitty records the expense and updates the user's personal balance according to FR-4.

## Verification Method

**Test:** The developer verifies that an unconfirmed expense does not affect the personal balance and that confirmation results in the expected expense recording and balance update. Verification takes place before merging the implementation.

## Supporting Information

- Stakeholder: “A confirm step sounds sensible to me — I'd rather check an amount once than chase it later.”
- Stakeholder: “I'd keep the confirm step either way.”
- **TBD:** The exact form of confirmation. The existing Coffee Kitty application does not have a frontend, so the interaction design remains open.
