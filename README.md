# JetTop

> [!WARNING]  
> JetTop is very much unfinished. Many features don't work or don't exist at all.
> See below for what *is* implemented.

*Free and open source alternative to the [WebTop Android app](https://play.google.com/store/apps/details?id=com.smartschool.webtop).*

Built with Jetpack Compose and Material 3 Expressive.

## Features

- Native Material 3 Expressive UI
- Support for both English and Hebrew
- Offline-first (info is cached when you're offline)
- [x] Better login
  - No Captcha required
  - No relogin required every single launch
    - Did you know they had an unused `rememberMe` flag in their login API this whole time?
- [x] Schedule
  - With identical lesson colors to official app/website
  - [ ] Rules
    - Rules allow you to modify the schedule, fixing typos, merging/removing lessons, and more.
- [x] Messages
  - [x] Details
    - [ ] Action buttons 
    - [ ] Contrast fix
      - Should fix yellow highlights and such having awful contrast in dark mode. 
  - [x] Downloads
  - [ ] Folders
  - [ ] Filters
  - [ ] Search
  - [ ] Multi-select
  - [ ] Compose
- [x] Student Card
  - [ ] Class Events
  - [ ] Events outside of class
  - [ ] Private lessons
  - [ ] Grades
- [ ] Notifications
- [ ] Settings

## LLM Use

This project does not use any LLMs outside being a fancy search engine and reverse engineering obfuscated JavaScript.
