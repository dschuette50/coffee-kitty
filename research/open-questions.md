# Coffee Kitty — Open Questions and Decisions

This document tracks unresolved requirements discovered during stakeholder interviews about expenses, payments and cash management. It is **not** an interview transcript and does **not** claim to provide verified citations. Add and verify the original Mattermost thread links manually before using any statements as evidence.

## Conventions

- **Conflict** — stakeholder statements support incompatible rules; a documented conflict does not automatically require keeping an otherwise well-supported working requirement open.
- **Working decision** — a sufficiently supported interpretation used for specification, with conflicting evidence preserved transparently.
- **Open** — the stakeholder has not specified the necessary business rule precisely.
- **Design decision** — implementation or UI choice; not automatically a missing stakeholder requirement.
- **Priority:** **High** may block specification or implementation; **Medium** affects correction workflows or edge cases; **Low** can be deferred.
- **Source:** `[add Mattermost thread link]` is deliberately a placeholder, **not** a citation.

## 1. Questions needing stakeholder clarification

### OQ-02 — How may members correct their own expenses? (Open · Medium · FR-7)

**Question:** Exactly how long may a member amend or delete their own expense after recording it? Is the proposed five-minute limit acceptable? Does a correction modify the original entry or reverse it and create a replacement?

**Known:** The stakeholder was receptive to a short correction window but did not unambiguously commit to an exact duration.

**Impact:** Permissions, timestamps, balance consistency and tests.

**Source:** [add Mattermost thread link]

### OQ-03 — What happens when an administrator corrects an expense? (Open · Medium · FR-7)

**Question:** Should the affected member be notified or shown an explicit record when an administrator deletes or replaces an expense? Must the original transaction remain available for audit purposes?

**Known:** Administrative corrections were discussed, but the visibility and audit requirements were not settled.

**Impact:** Transaction history, transparency and correction workflow.

**Source:** [add Mattermost thread link]

### OQ-04 — How are incorrect cash deposits reversed safely? (Open · High for FR-9)

**Question:** If an administrator deletes a deposit, must both the personal and cash balances be reversed? What should happen if reversing the deposit would make the cash balance negative, contrary to the cash-balance rule? Should deletion preserve an audit record?

**Known:** The administrator may delete erroneous deposits. The cash balance must never fall below zero. Their interaction in this edge case has not been specified.

**Impact:** Consistency, transactional integrity and authorization.

**Source:** [add Mattermost thread links]

### OQ-05 — What rules apply to cash payouts? (Open · Medium · FR-10/FR-11)

**Question:** May the administrator pay out only a member's positive credit, or are other payouts permitted? How should an insufficient cash balance be handled in the user workflow? Does every payout reduce both the member balance and the kitty balance?

**Known:** Admin-recorded refunds have been accepted in principle; cash withdrawals that would make the kitty balance negative must be rejected. Detailed eligibility and workflow remain unclear.

**Impact:** Payout validation and edge-case tests.

**Source:** [add Mattermost thread links]

### OQ-06 — How should initial cash and physical-cash corrections be recorded? (Open · Medium)

**Question:** How does the administrator enter the opening cash balance? How are later upward or downward cash corrections documented? Is a reason or history entry required, and are downward corrections subject to the same nonnegative-balance rule?

**Known:** The administrator needs to set/correct the shared cash balance when starting with existing cash or resolving discrepancies. The detailed procedure was left open.

**Impact:** Initial data, cash ledger and traceability.

**Source:** [add Mattermost thread links]

### OQ-07 — How are direct purchases using kitty cash represented? (Open · Medium)

**Question:** If supplies are bought directly using money taken from the shared cash box, is that a distinct cash expenditure, or should it be recorded through the same expense workflow as purchases paid privately by members? How do we avoid incorrectly crediting a member twice?

**Known:** Member-paid purchases credit their personal balance. Cash withdrawals reduce the kitty balance. The exact workflow connecting a cash-funded purchase to those transactions was not conclusively specified.

**Impact:** Accounting semantics and prevention of double-booking.

**Source:** [add Mattermost thread links]

### OQ-08: Description of Non-Bean Expenses

**Related Requirement:** FR-4 – Record User-Paid Expenses  
**Status:** Open  
**Priority:** Low

**Question:**  
Should non-bean expenses require a specific expense type
(e.g., milk, filters, repairs), or is the general category
"Other expense" sufficient when no note is provided?

**Context:**  
The stakeholder explicitly stated that expense notes are optional
(up to 500 characters). However, without a note, an expense
categorized as "Other" does not reveal what was purchased.

**Impact:**  
Determines whether additional input fields or categories are
required for non-bean expenses.

**Source:**  
[Add verified Mattermost thread links]

## 2. Working decisions — documented, not unresolved blockers

### WD-01 — Members record their own expenses (Working decision · FR-4; formerly OQ-01)

**Specification baseline:** A member may record an eligible expense paid with their own money. The member's personal balance is credited when the expense is recorded, **without prior administrator approval**. Administrators may correct erroneous expenses afterward; the precise correction rights and workflow remain open under OQ-02 and OQ-03.

**Why this is the working decision:** Multiple independent stakeholder conversations support member-entered expenses. In a further interview, the stakeholder was asked specifically about the administrative-approval proposal and the trust-versus-workload tradeoff, and preferred to avoid reviewing every individual expense. This is stronger evidence for the intended day-to-day workflow than counting unqualified yes/no answers alone.

**Contrary evidence (retained):** In the `bw467` conversation, the stakeholder also accepted an administrator-only approach and later repeated it in a summary. This contradiction remains on record but is **not treated as a blocking open question** for the current FR-4 draft. If new stakeholder evidence explicitly reverses the preference, revisit WD-01.

**Boundary:** Member entry is not permission to record another person's privately paid expense, nor permission to create a cash deposit. The confirmation step (FR-6) helps with accidental mistakes; it is not an anti-fraud approval mechanism.

**Sources:** [add your own Mattermost links for the member-entry interviews, your follow-up, and the `bw467` counterexample]

## 3. Implementation decisions — do not mislabel as stakeholder requirements

| Decision | Current status | Related requirement |
|---|---|---|
| How to present the confirmation step in a REST API without an existing frontend | Implementation choice; confirmation is supported, interaction design is not prescribed | FR-6 |
| Whether to use a free-text bean-name field or a searchable list allowing new names | Alternative UI ideas have been accepted in different conversations; avoid claiming a mandated widget | FR-4 |
| How to store monetary values and apply balance updates atomically | Engineering decision; correct euro-cent arithmetic and consistency are required | FR-4, FR-5, FR-9–FR-11 |
| How to display the member's balance and administrator's cash overview | UI/API decision; the underlying need to view balances is confirmed | Balance-display requirements |

## 4. Already clarified — do not ask again without a specific reason

| Topic | Current stakeholder finding | Source to add |
|---|---|---|
| Expense-recording permission | Members record their own privately paid expenses without prior admin approval (working decision WD-01; contrary `bw467` answer recorded) | [Mattermost links] |
| Member balance | May be negative; no debt limit has been set | [Mattermost link] |
| Cash balance | Must not become negative; exactly €0 is permitted | [Mattermost link] |
| Expense credit | Full privately paid expense credited immediately to payer; not split among other members | [Mattermost link] |
| Expense amount | €0.01–€1,000.00 | [Mattermost link] |
| Bean purchases | Bean name plus 100–1,000 g per package; multiple packages entered separately | [Mattermost link] |
| Expense note | Optional, maximum 500 characters | [Mattermost link] |
| Deposit permissions | Administrator only; cash paid to the administrator | [Mattermost link] |
| Deposit amount | Positive, at most €1,000 | [Mattermost link] |
| Deposit effect | Increases both personal and cash balance by the same amount | [Mattermost link] |
| Deactivated member | May still receive a cash deposit to settle debt | [Mattermost link] |
| Coffee price | Shared price initially €0.50; administrator may change it | [Mattermost link] |
| Price changes | Apply only to subsequently recorded coffees; old coffees retain their recorded price | [Mattermost link] |
| Price-change history | Stakeholder asked to retain when/how the price changed | [Mattermost link] |
| Out of scope for now | Receipt photographs and payment reminders | [Mattermost link] |

## 5. Deferred rather than unresolved

- **Detailed personal transaction history:** The stakeholder considered it useful but wanted to address current functionality first. Do not assume it is required for the immediate implementation.
- **Receipt uploads and reminders:** Explicitly deferred; do not silently implement them.

## 6. Research checklist

- [ ] Locate the original Mattermost threads manually.
- [ ] Add links and check exact stakeholder wording for each claimed finding.
- [ ] Preserve the administrator-only counterexample alongside the reasons for WD-01; do not erase contradictory evidence.
- [ ] Use WD-01 as the authorization baseline for FR-4 unless further evidence changes that decision.
- [ ] Resolve an open question only if it blocks the requirement currently being implemented; otherwise keep its status visible.
- [ ] Check requirement IDs against existing files in `requirements/` before renaming or cross-referencing FRs.
