# 🐶 PetStore API Automation

Uma suíte abrangente de testes automatizados para a API PetStore, projetada para garantir qualidade e confiabilidade dos endpoints.

## 🎯 Objetivo do Projeto
Realizar testes automatizados completos da API PetStore, cobrindo cenários de criação, consulta, atualização e exclusão de pets, orders e users.

## 📌 Tecnologias Utilizadas
- **Java 21** - Linguagem de programação principal
- **Maven** - Gerenciamento de dependências e build
- **TestNG** - Framework de testes
- **Rest Assured** - Biblioteca para testes de API REST
- **Extent Reports** - Geração de relatórios detalhados
- **GitHub Actions** - Integração contínua e entrega contínua (CI/CD)

## 🖥️ Pré-requisitos

### 💾 Requisitos Técnicos
- **Java Development Kit (JDK)** 21 ou superior
- **Maven** 3.8.1 ou superior
- **Git** 2.x ou superior

### 💡 Verificação de Instalação
Antes de iniciar, verifique as instalações:
```bash
java --version
mvn --version
git --version
```

## 🚀 Configuração do Projeto

### 1. Clonar Repositório
```bash
git clone https://github.com/seu-usuario/petstore-automation.git
cd petstore-automation
```

### 2. Instalar Dependências
```bash
mvn clean install -DskipTests
```

### 3. Configuração de Ambiente
- Verifique o arquivo `global.properties` em `src/test/resources/`
- Ajuste URLs, credenciais e configurações específicas conforme necessário

## 📊 Execução dos Testes

### 🔹 Modos de Execução

#### Executar Todos os Testes
```bash
mvn test -DsuiteXmlFile=suiteTest.xml
```

#### Executar Testes Específicos
```bash
# Executar uma classe de teste específica
mvn test -Dtest=PostCreatePetTest

# Executar um método de teste específico
mvn test -Dtest=PostCreatePetTest#testCreatePetSuccessfully
```

### 🐳 Execução com Docker (Opcional)
1. Construir imagem Docker
```bash
docker build -t petstore-automation .
```

2. Executar testes no container
```bash
docker run petstore-automation
```

## 📂 Estrutura do Projeto
```
petstore-automation/
├── src/
│   └── test/
│       ├── java/
│       │   └── br/com/petstore/
│       │       ├── objects/        # Modelos de dados da API
│       │       ├── requests/       # Implementações de requisições
│       │       ├── tests/          # Classes de testes automatizados
│       │       └── utils/          # Utilitários e helpers
│       └── resources/
│           └── global.properties   # Configurações globais
├── .github/workflows/
│   └── ci.yml                      # Pipeline de CI/CD
└── pom.xml                         # Configurações do Maven
```

## 📈 Relatórios de Teste

### 🔍 Localização
Relatórios detalhados são gerados em:
```
target/extent-reports/
```

### 📊 Interpretação de Relatórios
- **Visão Geral**: Resumo da execução dos testes
- **Detalhes do Teste**: Status de cada teste (Passou/Falhou)
- **Logs Detalhados**: Informações sobre falhas e exceções

## 🔄 Integração Contínua
- **Plataforma**: GitHub Actions
- **Gatilhos**:
   - Push na branch `master`
   - Pull Requests
- **Processo**: Execução completa de testes automatizados

## 🛠️ Troubleshooting

### Problemas Comuns
- **Falha na Conexão**: Verifique configurações de rede e URLs
- **Dependências**: Execute `mvn clean install`
- **Permissões**: Garanta permissões de execução em scripts

## 📚 Referências
- [PetStore Swagger API](https://petstore.swagger.io/)
- [Rest Assured Documentação](https://rest-assured.io/)
- [TestNG Documentação](https://testng.org/doc/)

## 🤝 Contribuição
1. Fork o projeto
2. Crie branch feature (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas alterações (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request