# FR-4: Record User-Paid Expenses

## Statement

When a group member records an expense they paid for the shared coffee group, Coffee Kitty MUST increase that member's personal balance by the recorded amount upon successful recording.

## Rationale

Group members purchase coffee beans and other supplies for the group using their own money. Allowing members to record these expenses reduces the administrator's workload and ensures that members receive credit for their contributions without requiring administrative approval for each expense.

## Success Criteria

- Recording an expense of €12.00 increases the paying member's balance from −€3.00 to €9.00.
- Recording an expense does not change another member's personal balance or the kitty's cash balance.
- Expense amounts from €0.01 to €1,000.00, inclusive, are accepted. Amounts outside this range are rejected without changing any balance.
- Expenses are recorded as either bean purchases or other group expenses.
- Bean purchases require a bean name and a weight between 100 g and 1,000 g, inclusive.
- Other group expenses do not require a bean name or weight.
- An optional note of up to 500 characters is accepted. Notes exceeding 500 characters are rejected.

## Verification Method

**Test:** Before merging the implementation, the developer executes automated tests using predefined member accounts and expense data in a test environment with PostgreSQL.

The tests verify the balance changes, the accepted amount and weight boundaries, required bean information, optional notes, and rejection of invalid input without unintended balance changes.

## Supporting Information

- The stakeholder confirmed that members may record expenses they paid for themselves, without prior administrator approval. [dschuette50 thread : "No — deposits aren't something users enter at all; I record those when someone hands me cash, and I can delete one again if I got it wrong. Expenses go the other way: the person who paid enters them, and I'd rather not have every single one run past me first, since checking them all would take me longer than counting strokes does today."]

- The stakeholder confirmed that the full expense amount is credited to the paying member's balance when the expense is recorded. [dschuette50 thread Beans aren't the only thing — there's also filters, milk, descaler and the odd repair, and those have no bean name or weight, while a bean purchase has the bean name and a weight of a few hundred grams or so. When someone buys something for the group with their own money, they record it as an expense with the amount (up to 1,000 €]
- The stakeholder specified expense amounts between €0.01 and €1,000.00, bean weights between 100 g and 1,000 g, and optional notes of up to 500 characters. [gediminas thread "The amount has to be between 0.01 € and 1,000 €, and the note is optional, up to 500 characters; photos of receipts I don't want for now, that's something for later. Otherwise nothing else comes to mind right away, the form is up to you anyway. Speaking of which — how would you handle the bean names: pick from the ones we've used before, or type a new one each time?"]
- **Historical discrepancy:** An earlier interview accepted administrator-only expense recording. Subsequent questioning and multiple other interviews support member-controlled expense recording. The selected workflow is documented in `open-questions.md`.
- **Related requirements:** FR-6 addresses confirmation before recording an expense. FR-7 addresses corrections to recorded expenses.
