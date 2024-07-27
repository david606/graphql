### Supported Versions



- 1.3.x on May-2024 is the current production line for use with Boot 3.3 and a GraphQL Java 22 baseline.
- 1.2.x on May-2023 is for use with Boot 3.1 and a GraphQL Java 20 baseline.
- 1.1.x on Nov-2022 is the first release based on Spring Framework 6, Jakarta EE, and Java 17 for use with Boot 3.0+ and GraphQL Java 19.
- 1.0.x on May-2022 is the first production release based on Spring Framework 5.3 and Java 8 for use with Boot 2.7+ and GraphQL Java 18.


官方资源:

1. 访问 GraphQL 官方网站，阅读官方文档，了解 GraphQL 的基本概念、查询语言、schema 设计等。
2. 学习 GraphQL 的基础知识，包括查询、变更（Mutations）、订阅（Subscriptions）以及 Schema 定义。
3. 查阅官方的教程和指南，特别是“Learn”部分，它提供了从入门到进阶的完整学习路径。

实践操作:

1. 使用在线工具如 GraphiQL 或 Apollo Studio 进行实践，尝试编写查询和变更。
2. 基于后端技术栈Java，选择GraphQL 库 graphql-java 搭建一个简单的 demo 项目。

案例研究:

1. 分析已成功应用 GraphQL 的知名项目或公司的案例，了解他们是如何解决特定问题的。
2. 阅读博客文章和论坛讨论，了解他人在实际应用中遇到的挑战和解决方案。

社区和资源:

1. 加入 GraphQL 的社区，如 Slack、Reddit、Stack Overflow 等，参与讨论，提问并解答问题。
2. 阅读书籍，如《Learning GraphQL》、《GraphQL in Action》等，深入理解最佳实践和高级主题。

评估与对比:

1. 对比 GraphQL 与 REST 的优缺点，理解在什么情况下 GraphQL 更合适。
2. 考虑到性能、安全性、维护成本等因素，评估引入 GraphQL 对现有系统的具体影响。

技术调研报告:

1. 总结调研结果，包括技术优势、实施难度、预期收益、潜在风险等，形成一份详细的技术调研报告。
2. 讨论并制定实施计划，包括短期和长期目标，以及如何与现有 REST API 共存或迁移的策略。

原型开发与测试:

1. 在一个小范围内或特定功能上试点实施 GraphQL，收集反馈，评估效果。



### 基本概念

#### 查询（Query）

查询是客户端用来从服务器请求数据的方式。它明确指定所需数据的结构和内容，从而减少网络请求次数。

```json
query {
  user(id: "123") {
    id
    name
    email
  }
}
```

#### 变更（Mutation）

变更用于在服务器上创建、更新或删除数据，是执行写操作的方式。

```json
mutation {
  createUser(input: {name: "Alice", email: "alice@example.com"}) {
    id
    name
  }
}
```

#### 类型系统（Type System）

定义数据的结构和规则，包括标量类型（如String, Int）、对象类型、接口、联合类型、枚举等。

```json
type User {
  id: ID!
  name: String!
  email: String!
}
```

#### Schema

Schema是GraphQL API的蓝图，描述了所有可用的查询、变更和数据类型。

```json
schema {
  query: Query
  mutation: Mutation
}
```

#### Resolver

后端函数，负责处理查询字段并返回数据。每个字段对应一个resolver

```json
DataFetcher<User> userFetcher = env -> {
  String id = env.getArgument("id");
  // 假设此处从数据库获取用户信息
  return getUserById(id);
};
```

#### Introspection

允许客户端查询Schema，自动发现API的结构和类型信息。

#### Scalars（标量类型）

基本数据类型，如字符串、整数、布尔值等。

#### Input Types

用于变更操作中输入数据的结构定义。

```json
input CreateUserInput {
  name: String!
  email: String!
}
```

#### Directives

查询中使用的指令，影响执行过程，如@include、@skip。

```json
query getUser($shouldIncludeEmail: Boolean!) {
  user(id: "456") {
    name
    email @include(if: $shouldIncludeEmail)
  }
}
```

#### Fragments

用于复用查询片段，提高代码可维护性。

```json
fragment userDetails on User {
  name
  email
}

query {
  user(id: "789") {
    ...userDetails
  }
}
```

### graphql 查询语言

GraphQL 查询语言是一种强类型、面向图形的数据查询语言，它允许客户端精确地获取所需的数据，以及描述数据间的关系。

#### 查询（Queries）

查询是最基本的GraphQL操作，用于从服务器获取数据。

```json
query {
  book(id: "1") {
    title
    author {
      name
      country
    }
    chapters {
      title
      pages
    }
  }
}
```

查询请求了一本书的信息，包括书名、作者的姓名和国籍，以及各章节的标题和页数。

#### 变更（Mutations）

变更用于修改服务器上的数据。

```json
mutation {
  createPost(input: {
    title: "GraphQL Introduction"
    content: "A detailed article about GraphQL..."
    authorId: "42"
  }) {
    id
    title
  }
}
```

此变更创建一篇新的文章，并返回创建后的文章ID和标题。

#### 变量（Variables）

变量可以让你在查询中使用动态值。

```json
query getBook($bookId: ID!) {
  book(id: $bookId) {
    title
    author {
      name
    }
  }
}
```

执行此查询时，需要提供一个名为 `$bookId` 的变量值。

#### 分页和连接（Pagination and Connections）

GraphQL支持分页和连接，帮助管理列表数据。

```json
query {
  books(first: 5) {
    edges {
      node {
        title
        author {
          name
        }
      }
    }
    pageInfo {
      hasNextPage
      endCursor
    }
  }
}
```

这里请求了前5本书的信息，并获取了分页信息以便进行下一页的查询。

#### 引用片段（Fragments）

片段用于复用查询或变更中的字段集合。

```json
fragment bookDetails on Book {
  title
  author {
    name
  }
}

query {
  recommendedBooks {
    ...bookDetails
  }
  recentBooks {
    ...bookDetails
  }
}
```

定义了一个名为bookDetails的片段，并在两个不同的查询字段中引用它。

#### Directives

Directives允许在查询中添加元数据或控制执行逻辑。

```json
query ($includeAuthor: Boolean!) {
  book(id: "2") {
    title
    author @include(if: $includeAuthor) {
      name
    }
  }
}
```

这里使用了@include指令，根据变量 `$includeAuthor` 的值决定是否包含作者信息。

### schema 设计

在GraphQL中，Schema设计是构建API的核心步骤，它定义了客户端可以查询和变更的数据结构。良好的Schema设计能够促进API的易用性、可维护性和性能。

####  理解数据模型

- 分析需求：首先，明确你的应用程序需要哪些数据以及这些数据之间的关系。
- 实体识别：识别出所有的实体（如用户、文章、评论等）和它们的属性（字段）

#### 定义类型系统

- 基础类型：使用GraphQL的内置标量类型（如String、Int、Float、Boolean、ID）定义简单字段。
- 自定义标量类型：如有必要，创建自定义标量类型来处理特定的数据格式（如日期时间）。
- 对象类型：为每个实体定义一个对象类型，包含其所有字段。
- 接口与联合类型：当多个类型共享相同的字段集时，使用接口；对于具有不同内部结构但需要被同等对待的类型，使用联合类型。
- 输入类型：为变更操作定义专门的输入类型，确保数据的结构和验证。

#### 查询与变更类型

- Query Type：定义客户端可以查询的所有数据字段。
- Mutation Type：定义客户端可以执行的所有数据变更操作。
- Subscription Type（可选）：用于实现实时数据更新，定义客户端可以订阅的事件。

#### 关系与嵌套查询

- 关系定义：通过字段来表示实体间的关联，使用Resolver函数处理这些关联查询。
- 嵌套查询：允许客户端在一个查询中请求相关联的数据，减少网络请求次数。

#### Resolver 函数

- 明确职责：每个字段都应该有一个对应的Resolver函数，负责数据的获取和处理。
- 数据来源：Resolvers可以访问数据库、调用外部API或处理内部逻辑。
- 性能优化：考虑数据加载策略，如Dataloader，以减少数据库查询次数。

#### Schema Directive

利用Directives：如@deprecated用于标记过时字段，@include和@skip控制字段的条件渲染。

#### Introspection

自动文档：利用GraphQL的内省功能，自动生成API文档，便于开发者理解和使用。

#### 示例

这段Schema设计了一个基本的社交平台模型，支持查询用户、帖子，创建用户、更新帖子，以及订阅新帖子的通知.

```json
// ==== 查询
// user: 通过提供一个ID参数，查询单个用户信息。
// posts: 可选地通过提供作者ID(authorId)，查询一系列帖子。
// -- [Post] 表示贴子列表。[] 表示数组
// -- 如果没有提供authorId，则可能查询所有公开的帖子或者根据实现逻辑决定查询范围。
type Query {
  user(id: ID!): User
  posts(authorId: ID): [Post]
}

// ==== 变更
// createUser: 创建一个新的用户
// -- 需要提供一个CreateUserInput类型的输入对象
// -- 其中包含用户的name和email，这两个字段都是必填的（带有感叹号!）。
// updatePost: 更新一个帖子
// -- 接收一个UpdatePostInput作为输入
// -- 包含帖子ID(postId)以及可选的标题(title)和内容(content)更新字段。
type Mutation {
  createUser(input: CreateUserInput!): User
  updatePost(input: UpdatePostInput!): Post
}

// ==== 订阅
// newPost: 订阅新帖子事件，当有新帖子发布时，如果指定了authorId，则只通知该作者的新帖子。
// 这是一个实时数据更新的功能，客户端可以通过订阅这个事件来实时获取新数据。
type Subscription {
  newPost(authorId: ID): Post
}

// ==== 数据模型

// User: 用户类型，包含用户ID(id)、用户名(name)、电子邮件(email)以及用户发布的帖子(posts)列表。
// @resolveReference注解通常用于告诉GraphQL如何处理引用类型，
// 虽然这不是标准的GraphQL指令，但可能是特定框架或库的约定，用于处理对象间的引用解析。
type User {
  id: ID!
  name: String!
  email: String!
  posts: [Post] @resolveReference
}

// Post: 帖子类型，包含帖子ID(id)、标题(title)、内容(content)、作者(author)以及关联的评论(comments)列表。
// 同样使用了@resolveReference来处理引用关系。
type Post {
  id: ID!
  title: String!
  content: String!
  author: User @resolveReference
  comments: [Comment] @resolveReference
}

// ==== 输入类型 

// CreateUserInput: 创建用户时所需的输入数据结构，包含必填的name和email字段。
input CreateUserInput {
  name: String!
  email: String!
}
// UpdatePostInput: 更新帖子时的输入数据结构，包含必须提供的postId以及可选的title和content字段。
input UpdatePostInput {
  postId: ID!
  title: String
  content: String
}
```

### 核心类

#### RuntimeWiring

在Java的GraphQL应用环境下，RuntimeWiring 是一个核心组件，它实质上是一个配置对象，负责将数据获取器（Data Fetchers）、类型解析器（Type Resolvers）及自定义标量（Custom Scalars）等关键元素整合在一起，以构建一个可执行的GraphQL服务。这些配置帮助框架理解如何针对GraphQL schema中定义的各种查询、变更和订阅等操作，执行具体的Java后端逻辑，从而在客户端请求时能够正确地检索或修改数据。

- **数据获取器 (Data Fetchers)**：它们直接关联到GraphQL schema中的字段，负责执行实际的数据检索逻辑。当GraphQL执行引擎遇到一个字段时，它会调用该字段对应的数据获取器来获取数据。在上述代码示例中，通过dataFetcher("queryUserById", ...)配置了一个数据获取器，用于处理查询特定用户的需求。
- **类型解析器 (Type Resolvers)**：当GraphQL查询涉及到接口、联合类型时，类型解析器用于确定实际的对象类型。尽管上述代码未直接展示类型解析器的配置，但它也是Runtime Wiring中的一个重要部分，确保查询结果能正确映射到GraphQL schema定义的类型系统上。
- **自定义标量 (Custom Scalars)**：GraphQL允许定义自定义标量类型来处理特定格式的数据，如日期、UUID等。Runtime Wiring中可以配置这些自定义标量的解析与序列化规则，确保数据在GraphQL层与Java层之间准确无误地转换。



直接在MyBatis中根据参数数量动态改变查询的字段列表并不直接支持，因为动态改变SELECT子句的字段在MyBatis设计中较为复杂，且可能导致SQL注入风险。

```xml
<sql id="selectId">b.id,</sql>
<sql id="selectName">b.name,</sql>
<sql id="selectPrice">b.price,</sql>
<!-- 字段SQL片段 -->

<select id="selectBookByDynamicFields" parameterType="map" resultMap="BookResultMap">
    SELECT
    <if test="fields.contains('id')">
        #{selectId}
    </if>
    <if test="fields.contains('name')">
        #{selectName}
    </if>
    <if test="fields.contains('price')">
        #{selectPrice}
    </if>
    <!-- 根据需要添加更多条件 -->
    
    FROM t_book b LEFT JOIN t_author a ON b.author_id = a.id
    <where>
        <if test="id != null">
            AND b.id = #{id}
        </if>
        <if test="name != null and name != ''">
            AND b.name = #{name}
        </if>
    </where>
</select>
```

### 调研目标

客户中去副本接口为公共接口，有如何问题：

1. 公共接口的使用背景，在不同业务系统、不同的使用场景下，产生不同的使用需求，体现在入参粒度、出参粒度
    - 尽可能满足在全字段范围内，用户指定使用哪些字段，就返回哪些字段的数据。
2. 公共接口是高频接口，会频繁的查询数据库，尽可能保证用户的入参，就是数据库要查询的字段，否则浪费磁盘IO
3. 公共接口是高频接口，还是远程接口，需要通过网络传输，尽量保证入参即出参，减少网络流量

### 调研结果

1. graphql 可以满足调研目标的第1、3点。

2. 调研目标的第2点，需要在查询数据库时，做一些前置的逻辑处理
3. 为满足调研目标的第2点，需要在服务层及Mybatis的层面做大量的逻辑处理，成本较高
4. 综合上面几点，最终结论为不引入 graphql

#### Graphql VS REST

##### GraphQL 特点：

1. 单一端点：GraphQL 允许通过单个 API 端点获取任何或所有数据点，减少了对多个端点的需求。
2. 减少数据过度获取：GraphQL 客户端可以精确请求所需的数据，避免不必要的数据传输。
3. 处理复杂系统和微服务：GraphQL 可以统一和隐藏集成多个系统的复杂性，支持微服务架构。
4. 快速安全：由于减少了数据传输量，GraphQL 请求和响应通常更快。

##### REST 特点：

1. 学习成本：RESTful API 相对简单，易于学习和理解。
2. 序列化：REST 提供了一种灵活的方法和格式来序列化 JSON 中的数据。
3. 缓存：REST API 可以通过 HTTP 代理服务器和缓存来管理高负载。
4. 复杂请求管理：REST API 对不同的请求有一个单独的端点，有助于管理复杂请求。
5. 简单性：REST API 设计简洁，易于维护。

##### 应用场景：

- GraphQL 适合：当需要精确控制数据获取、处理复杂系统或微服务架构时。
- REST 适合：当需要简单、易于理解和维护的 API 设计，以及强大的缓存和序列化支持时。

### 新技术的使用成本

1. 学习成本
    1. 官方文档（英文）
    2. 国内使用的公司暂时没有在网上到到
    3. 有书籍是英文版本
2. 使用成本
3. 风险成本
4. 时间成本

### 接入时机

建议在接口的基本功能可用后，再综合评估是否接入 graphql 。

- 可用性
- 性能





