# CI/CD Pipeline

## Overview
This project will use **GitHub Actions** to automate the **build, test, and deployment** processes. The CI/CD pipeline ensures code quality, runs integration tests, and deploys to different environments.

## 1. Workflow Structure
Sample GitHub Actions workflows are defined in the `github/workflows/` directory.

## 2. CI/CD Pipeline Overview
### **Continuous Integration (CI)**
- Runs **unit tests** and **integration tests** on every **push** and **pull request**.
- Uses a test database (**MySQL**) for integration tests.
- Ensures code quality before merging to `main`.

### **Continuous Deployment (CD)**
- On merging to `main`, the pipeline:
    - Builds a Docker image.
    - Pushes the image to a container registry.
    - Deploys to different environments (**development**, **staging**, and **production**) based on the branch.

## 3. Workflow Files
- **`ci-cd.yml`** – Automates build and deploys the application
- **`integration-tests.yml`** – Runs integration tests with a test database.

## 4. Rollback Mechanism
- The CI/CD pipeline monitors deployment **health metrics and alarms**.
- If a failure is detected post-deployment, an **automatic rollback** reverts to the last stable version.
