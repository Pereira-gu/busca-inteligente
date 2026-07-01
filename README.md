# 🔍 Busca Inteligente — Autocomplete & Cache-Aside Pattern

<div align="center">
  <img src="https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21">
  <img src="https://img.shields.io/badge/Spring_Boot-3.x-brightgreen?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot">
  <img src="https://img.shields.io/badge/Redis-RAM-red?style=for-the-badge&logo=redis&logoColor=white" alt="Redis">
  <img src="https://img.shields.io/badge/Tailwind_CSS-🎨-sky400?style=for-the-badge&logo=tailwindcss&logoColor=white" alt="Tailwind">
</div>

---

## ⚡ O Desafio da Performance
Implementar um campo de pesquisa que traz resultados em tempo real a cada tecla digitada cria um problema massivo: se o usuário digitar "Notebook" rapidamente, o frontend dispara 8 consultas pesadas com `LIKE %termo%` enfileiradas no banco de dados. Multiplique isso por mil usuários e sua infraestrutura entra em colapso.

## 💡 Estratégia de Resiliência Implementada

### 1. Otimização no Frontend (Algoritmo Debounce)
No JavaScript, adicionamos uma barreira de **Debounce de 300ms**. A aplicação monitora a digitação e só faz a requisição HTTP para a API quando o usuário faz uma pausa. Isso economiza mais de 70% de processamento inútil.

### 2. Estratégia Cache-Aside no Backend
* **Cache MISS (Primeira Busca):** A API vai ao banco relacional lento (com delay simulado de 1000ms), busca os dados, serializa em JSON usando Jackson e armazena no **Redis** com TTL de 10 minutos.
* **Cache HIT (Buscas Seguintes):** O resultado é interceptado e devolvido diretamente da memória RAM, ignorando completamente qualquer consulta ao banco principal.

---

## 📊 Comparativo Real de Performance

| Origem do Dado | Fluxo do Sistema | Latência Média | Impacto no Banco |
| :--- | :--- | :--- | :--- |
| **Banco de Dados Relacional** | `Cache MISS` ❌ | **1042ms** | Alto consumo de I/O |
| **Memória RAM (Redis)** | `Cache HIT` ⚡ | **2ms** | **ZERO IMPACTO** |

---

## 🚀 Como Testar o Ecossistema

1. **Inicie o Container do Redis:**
   ```bash
   docker run --name redis-busca -p 6379:6379 -d redis
Rode a API Spring Boot (Rodando na porta 8081 para evitar conflitos):

Bash
./mvnw spring-boot:run
Abra o Frontend:
Navegue até a pasta frontend e execute o arquivo index.html diretamente no seu navegador. Digite termos como "Notebook" ou "Smartphone" e veja o medidor de latência despencar em tempo real!