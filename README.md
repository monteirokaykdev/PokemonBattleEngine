# Pokémon Battle Engine (Java)

A turn-based Pokémon battle engine developed in Java, focused on reproducing core Pokémon battle mechanics while applying object-oriented design, clean architecture, and software engineering principles.

---

## Badges

![Java](https://img.shields.io/badge/Java-17+-orange)
![Maven](https://img.shields.io/badge/Maven-Build-red)
![Status](https://img.shields.io/badge/Status-In_Development-blue)
![OOP](https://img.shields.io/badge/Architecture-OOP-green)

---

## Overview

This project was created as a personal learning project to deepen knowledge of:

* Object-Oriented Programming (OOP)
* Software Architecture
* Design Patterns
* Clean Code
* Game Mechanics Implementation
* Java Collections
* Testing and Maintainability

The goal is to gradually build a complete Pokémon battle simulator while maintaining a clean and extensible codebase.

---

## Features

### Battle System

* Turn-based battles
* Speed priority system
* Random speed tie-breakers
* Turn counter
* HP tracking
* Fainting system

### Move System

* Physical moves
* Special moves
* Status moves
* Accuracy checks
* Critical hits
* STAB (Same Type Attack Bonus)
* Move selection menu

### Damage System

Implements the classic Pokémon damage formula:

```text
Damage =
((((2 × Level / 5 + 2) × Power × Attack / Defense) / 50) + 2)
× Modifiers
```

Modifiers currently implemented:

* Critical Hits
* STAB
* Type Effectiveness
* Random Factor (85% - 100%)

---

## Type System

### Complete Type Chart

* All Pokémon types implemented
* Type immunities
* Super-effective attacks
* Not very effective attacks
* Neutral interactions

### Dual-Type Support

The engine correctly handles dual typings by multiplying effectiveness values.

Examples:

```text
Fire → Grass = 2x
Fire → Water = 0.5x
Electric → Ground = 0x
Fire → Grass/Steel = 4x
```

---

## Status System

### Major Status Conditions

* Burn
* Poison
* Badly Poisoned (Toxic)
* Sleep
* Freeze
* Paralysis

### Volatile Status Conditions

* Confusion

### Mechanics Implemented

#### Burn

* Deals 1/16 max HP each turn

#### Poison

* Deals 1/8 max HP each turn

#### Badly Poisoned (Toxic)

Progressive damage:

```text
Turn 1 → 1/16 HP
Turn 2 → 2/16 HP
Turn 3 → 3/16 HP
Turn 4 → 4/16 HP
...
```

#### Sleep

* Lasts 1–3 turns
* Pokémon cannot act

#### Freeze

* 20% chance to thaw each turn

#### Paralysis

* 25% chance to be unable to move

#### Confusion

* Lasts 2–5 turns
* Chance to self-inflict damage
* Can coexist with major status conditions

---

## Status Immunities

Implemented status immunities:

| Status                  | Immune Types  |
| ----------------------- | ------------- |
| Burn                    | Fire          |
| Poison                  | Poison, Steel |
| Toxic                   | Poison, Steel |
| Thunder Wave            | Ground        |
| Grass-based Sleep Moves | Grass         |

---

## Architecture

The engine is divided into specialized components:

### Battle

Responsible for:

* Turn flow
* Move selection
* Speed priority
* Battle orchestration

### DamageCalculator

Responsible for:

* Damage formula
* STAB
* Type effectiveness
* Critical hits
* Random modifiers

### TypeChart

Responsible for:

* Type matchups
* Dual-type calculations
* Immunities

### StatusManager

Responsible for:

* Status application
* Status validation
* End-of-turn effects
* Action restrictions

### BattleRNG

Centralized randomization system:

* Accuracy checks
* Critical hits
* Status chances
* Sleep duration
* Confusion duration

---

## Project Structure

```text
src/
│
├── enums/
│   ├── PokemonType
│   ├── MoveCategory
│   └── Status
│
├── model/
│   ├── Pokemon
│   ├── PokemonSpecies
│   ├── Move
│   ├── Ability
│   ├── Stats
│   ├── Battle
│   ├── DamageCalculator
│   ├── TypeChart
│   ├── StatusManager
│   └── BattleRNG
│
└── demo/
    └── DemoApplication
```

---

## Example Usage

```java
Pokemon charmander = new Pokemon(createCharmander(), 5);
Pokemon squirtle = new Pokemon(createSquirtle(), 5);

Battle battle = new Battle(
    charmander,
    squirtle
);

battle.start();
```

---

## Example Battle

```text
====================
TURN 1
====================

Choose Squirtle's move:
1 - Water Gun

A critical hit!
STAB!
It's super effective!

Squirtle used Water Gun
Charmander HP: 181
```

---

## Roadmap

### Core Battle System

* [x] Turn-based battles
* [x] Damage calculation
* [x] Accuracy system
* [x] Critical hits
* [x] STAB
* [x] Type effectiveness
* [x] Dual typings

### Status System

* [x] Burn
* [x] Poison
* [x] Toxic
* [x] Sleep
* [x] Freeze
* [x] Paralysis
* [x] Confusion
* [x] Status immunities
* [X] Stat stages (+Attack, -Defense, etc.)

### Planned Features

* [ ] Weather system
* [ ] Ability effects
* [ ] Held items
* [ ] Multi-hit moves
* [ ] Recoil damage
* [ ] Healing moves
* [ ] Pokémon switching
* [ ] Teams and trainers
* [ ] AI opponent
* [ ] Experience system
* [ ] Level progression
* [ ] Unit tests

---

## Technologies

* Java 17+
* Maven
* Lombok

---

## Author

**Kayk Monteiro**

GitHub: https://github.com/monteirokaykdev

---

## Disclaimer

This project is a fan-made educational project created for learning purposes.

Pokémon and all related properties belong to Nintendo, Game Freak, and The Pokémon Company.
