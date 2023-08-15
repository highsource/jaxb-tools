# Contributing to JAXB Tools #

## Pull Requests preferences ##

Organizing pull requests allows us to more readily backport changes and support multiple releases.

* One commit per-PR
* PR rebased to current branch
* PR per-dependency change
* PR per-plugin changes
* PR per-Java JDK source/target changes
* PR per-feature change
* PRs must be signed by GitHub verified GPG or SSH key

## Code Style

* Spaces for whitespace -- XML spacing will be 2 character indentation and Java code 4. (see .editorconfig)

### Spaces v Tabs rationale

This projects works with data in mulitple file types-- .java, .xml, .xjb, etc.

Yes, using tabs saves in file size. 4-character spacing is the general consensus for Java programming.

However, the general sentiment has moved to 2 spaces for non-code files (.xml, .json, .yaml, etc) and having different whitespace charcaters for different file types is just weird.

