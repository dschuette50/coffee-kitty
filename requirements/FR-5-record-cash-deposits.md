# FR-5: Record Cash Deposits

## Statement

When an administrator records a cash deposit received from a member, Coffee Kitty MUST increase the member's personal balance and the kitty's cash balance by the deposited amount upon successful recording. 

## Rationale

Members pay for their coffee consumption by handing cash to the administrator. Recording deposits keeps the member balances and the shared cash balance consistent with the money received.

## Success Criteria

- Recording a deposit of €10.00 increases a member's balance from −€4.00 to €6.00 and the kitty's cash balance from €20.00 to €30.00.
- Coffee Kitty accepts positive deposit amounts of at most €1,000.00 and rejects zero, negative amounts and amounts exceeding €1,000.00.
- A non-administrator cannot record a deposit. An administrator can record a deposit for a deactivated member.

## Verification Method

**Test:** The developer runs automated tests against PostgreSQL before merging the implementation. The tests verify the changes to both balances, deposit amount boundaries, authorization restrictions and deposits for deactivated members.

## Supporting Information

- Stakeholder: “a deposit raises their balance and the kitty by the same amount, is always positive, and is at most 1,000 €” (Mattermost, bw467 thread).
- Stakeholder: “Only admins like me record deposits, and a deactivated person can still pay in so they can settle their debt” (Mattermost, bw467 thread).
- **TBD:** Whether additional validation or record-keeping requirements apply to deposit notes. Deleting incorrect deposits is covered by a separate proposed requirement.
