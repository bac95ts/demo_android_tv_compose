# Memory Maintenance Guidelines

## Purpose
This document provides rules for AI coding assistants to effectively manage context, retain important project knowledge, and avoid hallucinating features across long development sessions.

## 1. Context Retention & Context Window Management
- **Focus on the Active Task**: Only retain and process information strictly relevant to the current feature, bug, or refactor. Avoid pulling in unrelated files.
- **Incremental Understanding**: Read files incrementally. Start from the entry point of the feature (e.g., the specific Screen or ViewModel) and trace dependencies (Repository, UseCase, Data Source) only as needed.

## 2. Self-Updating Memory
- **Documentation Sync**: When you (the AI) introduce a new core architectural pattern, a major dependency, or a significant utility class, you MUST update the relevant documentation in this `.agent` folder.
- **State Tracking**: If a complex feature is partially implemented, create a temporary `.md` file in the `.agent` or a `docs/` folder outlining what is done and what remains, so future context has a clear starting point.

## 3. Avoiding Hallucinations & Assumptions
- **Verify Before Coding**: Always check `build.gradle.kts` for existing libraries before suggesting new ones. Do not assume a library (like Coil or Retrofit) is present or configured in a specific way without verifying.
- **Respect the TV Ecosystem**: Android TV APIs and Compose TV APIs differ from mobile. If unsure about a specific `androidx.tv` component's behavior or signature, prioritize searching official documentation or the provided reference repositories over guessing.
