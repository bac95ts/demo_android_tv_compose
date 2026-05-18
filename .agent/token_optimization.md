# Token Optimization Strategies

## Purpose
To ensure efficient, fast, and cost-effective communication, the AI assistant must adhere to these token optimization strategies. Minimizing token usage prevents context limits from being reached prematurely.

## 1. Precise Code Modifications
- **No Full File Rewrites**: NEVER output an entire file if you are only changing a few lines.
- **Use Targeted Tools**: Always use specialized file editing tools (like `replace_file_content` or `multi_replace_file_content`) to apply targeted, surgical edits.
- **Provide Snippets**: When demonstrating a concept or suggesting an approach, output only the relevant function, class, or Compose block. Omit surrounding boilerplate and standard imports unless explicitly asked.

## 2. Concise Communication
- **Direct Responses**: Keep explanations brief, direct, and highly technical. Skip conversational filler, pleasantries, and lengthy introductions.
- **Avoid Echoing**: Do not repeat the user's prompt or requirements back to them. Acknowledge the task by simply doing it or providing a short action plan.
- **Summary Overviews**: When completing a multi-step task, provide a concise bulleted list of what was changed, rather than a paragraph detailing the process.

## 3. Code Generation Efficiency
- **Leverage Existing Code**: Reuse existing utility functions, extensions, and UI components instead of writing new ones from scratch.
- **Minimize Comments**: Only add comments for complex algorithms or non-obvious business logic. Avoid redundant comments that describe what the code does clearly (e.g., avoid `// Fetch data from API`).
