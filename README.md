🧮 Calculadora Full Stack - Cloud Run & Spring Boot
Status do Projeto

✅ Concluído | 🌐 Acesse: https://calculadora-backend-161579150165.us-central1.run.app/ 
💻 Visão Geral

Este projeto é uma demonstração de uma arquitetura Full Stack moderna e escalável, projetada para processar cálculos complexos. Ele utiliza o Frontend interativo do Firebase para se comunicar com um Backend robusto construído em Java/Spring Boot e implantado de forma serverless no Google Cloud Run.

O principal foco técnico do projeto foi garantir a comunicação segura e eficiente (CORS) entre os serviços em diferentes plataformas do Google Cloud, superando os desafios de segurança de rede inerentes a esse tipo de arquitetura.
🏛️ Arquitetura

A aplicação é dividida em dois serviços principais que se comunicam via API REST:

Componente
	

Tecnologia
	

Plataforma de Hospedagem
	

Função Principal

Frontend
	

HTML, Tailwind CSS, JavaScript
	

Firebase Hosting
	

Interface do usuário, lógica de i18n (PT/EN) e envio de expressões.

Backend
	

Java 21, Spring Boot 3.3, exp4j
	

Google Cloud Run (Container Docker)
	

Recebe expressões via API REST (/calcular), avalia o resultado e retorna ao Frontend.
✨ Funcionalidades

    Processamento Serverless: Toda a lógica de cálculo é processada pelo Backend em Java, que só consome recursos de nuvem quando necessário (pago por uso).

    Internacionalização (i18n): Suporte nativo para troca de idioma entre Português (PT) e Inglês (EN) na interface.

    Operações Avançadas: Suporte a parênteses, raízes quadradas (√), potências (^), além das quatro operações básicas (+, -, *, /).

🛠️ Tecnologias Utilizadas

Backend:

    Linguagem: Java 21

    Framework: Spring Boot 3.3

    Cálculos: exp4j (biblioteca para avaliação de expressões)

    Container: Docker

Frontend:

    Linguagens: HTML5, JavaScript

    Estilização: Tailwind CSS

    Deploy: Firebase Hosting

Infraestrutura Cloud:

    Google Cloud Run (Serviço Serverless)

    Google Cloud Build (CI/CD para Imagens Docker)

    Google Cloud CLI (gcloud)

🚧 Desafio Crítico Superado: Sincronização CORS

O maior desafio técnico foi a persistência do erro 403 CORS Missing Allow Origin, que ocorria na camada de infraestrutura do Google Cloud Run, e não no código Java.

Descobrimos que as flags explícitas de CORS do gcloud CLI eram incompatíveis com a versão utilizada (dando unrecognized arguments), forçando uma solução de contorno na política de tráfego.

A Chave da Estabilidade (Fluxo Mandatório):

Após qualquer deploy (seja do Backend ou do Frontend), é mandatório executar o comando abaixo para forçar o Cloud Run a liberar o tráfego de entrada (--ingress all), reestabelecendo a comunicação com o domínio do Firebase:

# Comando essencial para sincronizar a política de segurança do Cloud Run:
gcloud run services update calculadora-backend \
    --region us-central1 \
    --platform managed \
    --ingress all 

🚀 Guia de Setup e Deploy

Siga os passos abaixo para configurar e implantar o projeto (assumindo que o Firebase CLI e o Google Cloud CLI (gcloud) estejam autenticados).
1. Deploy do Backend (Java)

    Crie a Imagem Docker: (Substitua PROJECT-ID pelo seu ID do projeto Google Cloud)

    gcloud builds submit --tag gcr.io/PROJECT-ID/calculadora-backend .

    Implantar no Cloud Run:

    gcloud run deploy calculadora-backend \
        --image gcr.io/PROJECT-ID/calculadora-backend \
        --platform managed \
        --region us-central1 \
        --allow-unauthenticated \
        --port 8080

    Obter a URL: Anote a URL gerada.

2. Deploy do Frontend (Firebase)

    Atualize o index.html:

        Abra src/main/resources/static/index.html.

        Localize a linha const CLOUD_RUN_URL = "...".

        Substitua o valor pela URL do Cloud Run obtida no Passo 1.3.

    Deploy no Firebase Hosting:

    firebase deploy --only hosting

3. Sincronização Final (Obrigatória)

Após o deploy do Frontend, execute o comando de sincronização para garantir que o Cloud Run não bloqueie o Firebase:

gcloud run services update calculadora-backend \
    --region us-central1 \
    --platform managed \
    --ingress all 
