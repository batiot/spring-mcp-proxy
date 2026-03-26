# GitHub Copilot Instructions

This project uses [OpenSpec](https://github.com/Fission-AI/OpenSpec) to manage feature requests and implementation work.

## OpenSpec Workflow

When asked to explore, plan, or implement any new feature or change, follow the OpenSpec workflow:

### 1. Explore (before planning)

Use `/opsx:explore` to understand the current codebase before proposing any change.

### 2. Propose (plan before coding)

Use `/opsx:propose "<change-name>"` to create a full change proposal with:
- `proposal.md` — the *why* and *what*
- `design.md` — the *how* (technical approach)
- `tasks.md` — the implementation checklist

**Never start implementing before a proposal is agreed upon.**

### 3. Apply (implement)

Use `/opsx:apply` to implement the tasks defined in `tasks.md`, working through each item in order.

### 4. Archive (close the change)

Use `/opsx:archive` once all tasks are complete and the change is verified.

---

## Key Rules

- **Agree before you build** — always create a proposal and get alignment before writing code.
- **One change at a time** — each feature lives under `openspec/changes/<change-name>/`.
- **Specs are the source of truth** — `openspec/specs/` describes current system behavior.
- **Iterate freely** — you can update any artifact (`proposal.md`, `design.md`, `tasks.md`) as you learn more during implementation.

## Available Slash Commands

| Command | Purpose |
|---|---|
| `/opsx:explore` | Explore the codebase and summarize relevant context |
| `/opsx:propose <name>` | Create a full change proposal (proposal + design + tasks) |
| `/opsx:apply` | Implement the tasks from the current change |
| `/opsx:archive` | Archive the completed change |
