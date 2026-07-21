export interface CPStatsData {
  platform: string;
  handle: string;
  maxRating: string;
  title: string;
  solveCount: string;
  profileUrl: string;
  color: string;
}

export interface Achievement {
  year: string;
  title: string;
  event: string;
  team?: string;
  location: string;
  highlight?: boolean;
}

export interface ProjectData {
  id: string;
  title: string;
  tools: string[];
  description: string[];
  category: 'Backend' | 'Systems' | 'Web' | 'All';
  githubUrl?: string;
  demoUrl?: string;
  stars?: number;
  forks?: number;
}

export interface ExperienceData {
  period: string;
  role: string;
  organization: string;
  location: string;
  commitHash: string;
  branch: string;
  description: string[];
  tags: string[];
  insertions: number;
  deletions: number;
}

export const SIAM_DATA = {
  name: "Md. Siam",
  handle: "md5iam",
  role: "Competitive Programmer & Software Engineer",
  subRole: "Backend Architecture & Algorithmic Problem Solving",
  location: "Dhaka, Bangladesh",
  email: "muhammad.siam.tech@gmail.com",
  github: "https://github.com/md5iam",
  linkedin: "https://linkedin.com/in/md5iam",
  codeforces: "https://codeforces.com/profile/5iaM_",
  codechef: "https://www.codechef.com/users/md5iam",
  atcoder: "https://atcoder.jp/users/beatrix_coder",

  kernelVersion: "v3.8.3 ONLINE",

  bioLog: [
    "I am a Computer Science student & Competitive Programmer passionate about low-latency backend systems, clean OOP architecture, and algorithm optimization.",
    "Solved 2000+ algorithmic problems across online judges. Qualified as an ICPC Regionalist and Meta Hacker Cup Round 2 participant.",
    "Engineered robust REST APIs & microservices using Go (Gin/GORM), Python (FastAPI/SQLAlchemy), and Java (OOP/MySQL)."
  ],

  education: [
    {
      institution: "Southeast University (SEU)",
      degree: "B.Sc. in Computer Science and Engineering",
      period: "July 2023 – July 2027",
      location: "Dhaka, Bangladesh",
      detail: "CGPA: 3.83 / 4.00 (Up to 9th Semester)"
    },
    {
      institution: "Haziganj Model Govt. College",
      degree: "Higher Secondary Certificate (Science)",
      period: "May 2019 – May 2021",
      location: "Haziganj, Chandpur",
      detail: "GPA: 5.00 / 5.00"
    }
  ],

  statsCounters: {
    solvedProblems: "2000+",
    icpcRegionalist: "2025",
    cgpa: "3.83",
    maxRating: "1534"
  },

  skills: {
    languages: ["C++", "C", "Java", "Go", "Python", "Bash"],
    web: ["FastAPI", "Gin", "HTML", "CSS", "React", "TypeScript"],
    database: ["MySQL", "PostgreSQL", "SQLite"],
    tools: ["VS Code", "Git", "GitHub", "Docker", "Linux", "Postman"],
    conceptual: ["Pandas", "NumPy", "Matplotlib", "Seaborn", "OOP", "Data Structures", "Algorithms"]
  },

  cpPlatforms: [
    {
      platform: "Codeforces (Contest)",
      handle: "5iaM_",
      maxRating: "1405",
      title: "Specialist",
      solveCount: "200+",
      profileUrl: "https://codeforces.com/profile/5iaM_",
      color: "#03a9f4"
    },
    {
      platform: "Codeforces (Practice)",
      handle: "Muhammad_Siam",
      maxRating: "1244",
      title: "Pupil",
      solveCount: "1400+",
      profileUrl: "https://codeforces.com/profile/Muhammad_Siam",
      color: "#4caf50"
    },
    {
      platform: "AtCoder",
      handle: "beatrix_coder",
      maxRating: "225",
      title: "Gray",
      solveCount: "160+",
      profileUrl: "https://atcoder.jp/users/beatrix_coder",
      color: "#808080"
    }
  ] as CPStatsData[],

  achievements: [
    {
      year: "2025",
      title: "ICPC Asia Dhaka Regional",
      event: "Qualified as Regionalist",
      team: "AlgoFlare ≫ SEU",
      location: "Dhaka, Bangladesh",
      highlight: true
    },
    {
      year: "2025",
      title: "Meta Hacker Cup",
      event: "Qualified for Round 2",
      location: "International",
      highlight: true
    },
    {
      year: "2024",
      title: "31st Place, BUET IUPC 2024",
      event: "BUET IUPC 2024",
      team: "Team SEU1",
      location: "Dhaka, Bangladesh"
    },
    {
      year: "2025",
      title: "19th Place, CodeClash",
      event: "Oscillon 2025",
      team: "AlgoFlare ≫ SEU",
      location: "Dhaka, Bangladesh"
    },
    {
      year: "2025",
      title: "51st Place, BUBT IUCPC",
      event: "BUBT Inter-University Contest 2025",
      team: "AlgoFlare ≫ SEU",
      location: "Dhaka, Bangladesh"
    },
    {
      year: "2024–26",
      title: "National IUPCs Participant",
      event: "SUST IUPC 2026, BUET IUPC 2026, AUST IUPC 2025, DUET IUPC 2025, UU IUPC 2025, CUET IUPC 2025",
      location: "Dhaka, Bangladesh"
    }
  ] as Achievement[],

  experiences: [] as ExperienceData[],

  projects: [
    {
      id: "codetrack",
      title: "CodeTrack",
      tools: ["React", "Vite", "Supabase", "PostgreSQL", "JavaScript", "HTML", "CSS"],
      description: [
        "A personal competitive programming contest tracker built for athletes who compete on Codeforces, AtCoder, and LeetCode.",
        "React + Vite frontend framework with Supabase (PostgreSQL) cloud database for cross-device sync & localStorage fallback.",
        "CORS proxy failover with multi-proxy parallel racing to fetch AtCoder contest data in production."
      ],
      category: "Web",
      demoUrl: "https://md5iam.github.io/CodeTrack/",
      githubUrl: "https://github.com/Md5iam/CodeTrack",
      stars: 28,
      forks: 8
    },
    {
      id: "ecom-backend",
      title: "Ecommerce (Backend)",
      tools: ["Java 17", "Spring Boot", "Spring Security", "PostgreSQL", "JPA / Hibernate", "JWT", "REST API", "Postman"],
      description: [
        "Core Backend: Built with Java 17, Spring Boot 4.0.6, Spring Web MVC REST API controllers, and Maven dependency management.",
        "Persistence & DB: PostgreSQL with Spring Data JPA & Hibernate ORM using Repository pattern & automatic DDL schema updates.",
        "Security & Auth: Spring Security with HTTP Cookie stored JWT authentication, BCrypt password hashing, stateless sessions, and role-based authorization (ROLE_USER, ROLE_SELLER, ROLE_ADMIN).",
        "Supporting Architecture: ModelMapper DTO conversion, Lombok boilerplate reduction, Jakarta Bean Validation, and Spring Security Test suite."
      ],
      category: "Backend",
      githubUrl: "https://github.com/Md5iam/Spring-Boot-Practice/tree/main/sb-ecom",
      stars: 35,
      forks: 9
    },
    {
      id: "courier-system",
      title: "Courier Management System",
      tools: ["Java", "MySQL", "OOP", "JavaFX"],
      description: [
        "Designed and built a full courier management software in Java using strict Object-Oriented Programming (OOP) principles and JavaFX.",
        "Integrated MySQL database for persistent storage of parcel status, customer metadata, and delivery logs with real-time parcel tracking."
      ],
      category: "Systems",
      githubUrl: "https://github.com/Md5iam/Java/tree/main/Java%20Final%20Project/CourierManagmentSystem(Final)",
      stars: 18,
      forks: 5
    },
    {
      id: "dungeon-quest",
      title: "The-Dungeon-Quest",
      tools: ["C++", "OpenGL", "GLUT", "GLU", "3D Graphics", "Linux", "g++"],
      description: [
        "A 3D first-person adventure quest game built entirely with OpenGL (GLUT) and C++.",
        "The player must explore a mysterious dungeon, solve unique puzzles in each chamber, collect keys, and escape through correct doors.",
        "Rendered in real-time 3D with textured environments, dynamic lighting engine, and atmospheric shaders (Prerequisites: Linux Ubuntu, g++, OpenGL, GLU, GLUT)."
      ],
      category: "Systems",
      githubUrl: "https://github.com/Md5iam/The-Dungeon-Quest",
      stars: 21,
      forks: 6
    },
    {
      id: "todo-book-api",
      title: "Todo Book API Service",
      tools: ["FastAPI", "PostgreSQL", "SQLAlchemy", "JWT", "Pytest"],
      description: [
        "Developed a high-concurrency Todo management backend API using FastAPI and PostgreSQL with SQLAlchemy ORM.",
        "Implemented JWT-based token authentication for secure registration, login, and route authorization.",
        "Achieved high test coverage with automated unit and integration tests using Pytest."
      ],
      category: "Backend",
      githubUrl: "https://github.com/Md5iam/Java/tree/main/Java%20Final%20Project/CourierManagmentSystem(Final)",
      stars: 24,
      forks: 7
    },
    {
      id: "crud-rest-go",
      title: "Go REST API & GORM Engine",
      tools: ["Go", "Gin", "GORM", "PostgreSQL"],
      description: [
        "Built a modular RESTful microservice API in Go leveraging the Gin framework and GORM ORM layer.",
        "Designed optimized SQL query handling with PostgreSQL for low latency under concurrent user load.",
        "Structured standard CRUD endpoints with structured error handling and strict HTTP status code mapping."
      ],
      category: "Backend",
      githubUrl: "https://github.com/Md5iam/CRUD-API-in-GoLang-",
      stars: 32,
      forks: 11
    }
  ] as ProjectData[]
};
