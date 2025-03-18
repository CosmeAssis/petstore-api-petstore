# 🐶 PetStore API Automation

Este projeto é uma suíte de testes automatizados para a API PetStore utilizando **Java 21**, **TestNG** e **Rest Assured**.

## 📌 Tecnologias Utilizadas
- **Java 21** - Linguagem de programação
- **Maven** - Gerenciador de dependências e build
- **TestNG** - Framework de testes
- **Rest Assured** - Framework para testes de API REST
- **Extent Reports** - Geração de relatórios detalhados
- **GitHub Actions** - Integração contínua e entrega contínua (CI/CD)

## 🚀 Como Configurar o Projeto

### 🔹 Pré-requisitos
- Java 21 JDK instalado
- Maven instalado
- Git instalado

### 🔹 Passos para Configuração

1. **Clonar o Repositório**
   ```bash
   git clone https://github.com/seu-usuario/petstore-automation.git
   cd petstore-automation
   ```

2. **Instalar Dependências**
   ```bash
   mvn clean install
   ```

## 📊 Como Executar os Testes

### 🔹 Executar Todos os Testes
```bash
mvn test -DsuiteXmlFile=suiteTest.xml
```

### 🔹 Executar Testes Específicos
```bash
mvn test -Dtest=NomeDaClasseDeTeste
```

## 📝 Estrutura do Projeto
```
petstore-automation/
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   ├── br/com/petstore/
│   │   │   │   ├── objects/        # 📂 Contém os objetos da API (ex: PetObject, OrderObject)
│   │   │   │   ├── requests/       # 📂 Contém as requisições HTTP (ex: PostCreatePetRequest, GetPetRequest)
│   │   │   │   ├── tests/          # 📂 Contém os testes automatizados (ex: PostCreatePetTest, GetPetTest)
│   │   │   │   └── utils/          # 📂 Contém classes utilitárias (ex: RestAssuredUtils, PropertiesLoader)
│   │   └── resources/
│   │       └── global.properties   # 📄 Configuração global da API e relatórios
├── .github/
│   └── workflows/
│       └── ci.yml                  # 📄 Pipeline do GitHub Actions
├── pom.xml                         # 📄 Configurações do Maven
├── suiteTest.xml                   # 📄 Arquivo de configuração do TestNG
└── README.md                       # 📄 Documentação do projeto
```

## 📈 Relatórios
Os relatórios de testes são gerados automaticamente após a execução dos testes e podem ser encontrados em:
```
target/extent-reports/
```

## 🔄 CI/CD
Este projeto utiliza GitHub Actions para integração contínua. Os testes são executados automaticamente a cada push ou pull request para a branch master.

## 📚 Documentação de Referência
- [Documentação da API PetStore](https://petstore.swagger.io/)
- [Rest Assured Documentation](https://rest-assured.io/)
- [TestNG Documentation](https://testng.org/doc/)

## 🤝 Contribuição
1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/nova-feature`)
3. Faça commit das suas alterações (`git commit -m 'Adiciona nova feature'`)
4. Faça push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request