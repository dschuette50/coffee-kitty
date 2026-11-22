# Coffee Kitty – Stakeholder Research Summary

## 1. Purpose

This document consolidates findings from stakeholder interviews concerning expense recording, cash deposits, personal balances, cash management, and error correction.

It distinguishes confirmed requirements, conflicting statements, unresolved decisions, and proposed solutions.

This document is not itself a functional requirement specification.

## 2. Core Domain Concepts

### Personal Balance

A member's personal balance represents their financial position relative to the coffee group.

- Coffee consumption decreases the balance.
- Cash deposits increase the balance.
- Privately paid group expenses increase the balance.
- Cash payouts decrease the balance.
- Negative personal balances are permitted.

### Cash Balance

The cash balance represents the money held in the shared coffee kitty.

- Cash deposits increase the cash balance.
- Cash withdrawals decrease the cash balance.
- Privately paid expenses do not directly affect the cash balance.
- The cash balance must never become negative.

## 3. Expense Recording

### Confirmed Findings

- Group members purchase supplies using their own money.
- The full expense amount is credited to the paying member.
- The credit is applied when the expense is recorded.
- Expenses include coffee beans and other supplies.
- Expense amounts must be between €0.01 and €1,000.00.
- Bean purchases require a bean name and weight.
- Bean package weights range from 100 g to 1,000 g.
- Notes are optional and limited to 500 characters.
- Receipt photographs are not required.

### Conflicting Findings

Some stakeholder responses support members recording their own expenses without prior administrator approval.

Other responses accept administrator-only expense recording.

The preferred workflow identified during the later interview is member-controlled expense recording without mandatory administrator approval.

**Status: UNRESOLVED**

### Design Considerations

Mandatory administrator approval would increase administrative workload.

A user confirmation step could reduce accidental entry errors without introducing an administrator approval process.

Confirmation does not prevent deliberate fraudulent entries.

## 4. Cash Deposits

### Confirmed Findings

- Members hand cash to the administrator.
- Only administrators record deposits.
- Deposits must be positive and must not exceed €1,000.00.
- A deposit increases the member's personal balance.
- The same deposit increases the shared cash balance.
- Administrators can record deposits for deactivated members.
- Administrators can delete incorrect deposit entries.

## 5. Error Prevention and Correction

### Confirmed Findings

- The stakeholder accepts a confirmation step before recording expenses.
- Members should have a short period to correct their own entries.
- Administrators can correct incorrect expenses by deleting the original entry and recording a replacement.
- Administrators can delete incorrect deposits.

### Unresolved Decisions

- Exact correction period for members.
- Detailed correction permissions.
- Whether affected members are notified of administrative corrections.
- Whether deleted transactions remain visible in an audit history.

## 6. Cash Withdrawals and Refunds

### Confirmed Findings

- Cash may be withdrawn to purchase supplies.
- Cash withdrawals must not produce a negative cash balance.
- A cash balance of exactly zero is permitted.
- The administrator may record cash payouts.

### Unresolved Decisions

- Detailed refund workflow for departing members.
- Handling insufficient cash for a requested refund.
- Whether refund-specific transaction categories are required.
- How corrections to cash withdrawals are recorded.

## 7. Coffee Pricing

### Confirmed Findings

- Coffee has one common price for all members.
- The initial price is €0.50 per cup.
- The price does not depend on the coffee bean variety.
- The price can be changed.

### Unresolved Decisions

- Whether price changes affect previously recorded consumption.
- The effective time of price changes.

## 8. Requirement Candidates

| ID | Requirement | Status |
|---|---|---|
| FR-4 | Record User-Paid Expenses | Draft |
| FR-5 | Record Cash Deposits | Draft |
| FR-6 | Confirm Expense Entry | Proposed |
| FR-7 | Correct User-Recorded Expenses | Proposed |
| FR-9 | Delete Incorrect Cash Deposits | Proposed |
| FR-10 | Record Cash Payouts | Proposed |
| FR-11 | Prevent Negative Cash Balance | Proposed |
| FR-12 | Display Personal Balance | Proposed |
| FR-13 | Display Cash Balance | Proposed |
| FR-14 | Manage Coffee Price | Proposed |

All identifiers are provisional and must be checked against the existing repository.

## 9. Source Documentation

The final version must include the relevant Mattermost thread links for each finding.

Statements from different interviews must remain distinguishable.

Conflicting stakeholder statements must not be silently reconciled.

## 10. Implementation Scope

For Assignment 4.2, only one of the formally specified requirements must be implemented.

The remaining requirements document the broader stakeholder research and possible future development.

Implementation decisions must not be treated as stakeholder requirements unless they are supported by interview evidence.
