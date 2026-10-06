# Orazaka Platform Contracts

> Tier-1 platform contracts shared by every Orazaka service: the job plane (jobs-api) and the application-persistence ports (persistence-app-api). Pure interfaces and records, zero implementation.

**Layer:** Foundation — reusable by any Krizaka application · **Version:** `1.0.0-SNAPSHOT` · **License:** Apache-2.0 ·
part of the [Orazaka platform](https://github.com/krizaka/orazaka) by [Krizaka](https://krizaka.com)

## What it provides

| Module | Artifact | Role |
|:---|:---|:---|
| `orazaka-jobs-api` | `com.orazaka:orazaka-jobs-api` | Job plane contract: job commands/events, lanes, typed failure causes, capability descriptors. |
| `orazaka-persistence-app-api` | `com.orazaka:orazaka-persistence-app-api` | Application-persistence ports & DTOs (chat, jobs, models, configs, outbox) implemented by `orazaka-persistence-app`. |

Domain contracts live with their domain: `orazaka-identity-api` in
[orazaka-users](https://github.com/krizaka/orazaka-users), `orazaka-billing-api` in [orazaka-billing](https://github.com/krizaka/orazaka-billing),
`orazaka-studio-api` in [orazaka-studio](https://github.com/krizaka/orazaka-studio).

## Rules

- Tier-1 (AGENTS.md §2): **pure interfaces and records**, zero implementation dependency.
- Semver'd: a breaking change here is a breaking change for every service.

## Position in the platform

| | |
|:---|:---|
| Depends on | [`orazaka-build`](https://github.com/krizaka/orazaka-build) |
| Used by | [`orazaka-studio`](https://github.com/krizaka/orazaka-studio) · [`orazaka-ai-engine`](https://github.com/krizaka/orazaka-ai-engine) · [`orazaka-conversation-service`](https://github.com/krizaka/orazaka-conversation-service) · [`orazaka-job-service`](https://github.com/krizaka/orazaka-job-service) |
| Workspace path | `orazaka-libs/orazaka-contracts` |

## Build

**Inside the Orazaka workspace** (recommended — every dependency is built from source):

```bash
git clone https://github.com/krizaka/orazaka.git && cd orazaka
node scripts/workspace.mjs clone          # clones every repository at its workspace path
./mvnw -f orazaka-libs/orazaka-contracts/pom.xml verify
```

**Standalone** — upstream artifacts must be in `~/.m2` (built by the workspace) or resolvable from
GitHub Packages (`https://maven.pkg.github.com/krizaka/<repository>`, see the
[workspace README](https://github.com/krizaka/orazaka#consuming-packages)):

```bash
./mvnw verify
```

Requirements: JDK 21, Docker (Testcontainers integration tests).

## Governance

This repository follows the Orazaka governance contract — [AGENTS.md](https://github.com/krizaka/orazaka/blob/main/AGENTS.md)
in the workspace is normative; the local [AGENTS.md](AGENTS.md) only scopes it to this repository.

## License

Apache License 2.0 — see [LICENSE](LICENSE) and [NOTICE](NOTICE).
