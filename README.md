# 공연 티켓 판매 서비스

📅 **2024년 8월 ~ 2024년 9월**

![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=Spring%20Boot&logoColor=white)
![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-6DB33F?style=for-the-badge&logo=&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=Spring%20Security&logoColor=white)
![JPA](https://img.shields.io/badge/JPA-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![QueryDSL](https://img.shields.io/badge/QueryDSL-0769AD?style=for-the-badge&logo=Java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white)
![Flyway](https://img.shields.io/badge/Flyway-CC0200?style=for-the-badge&logo=Flyway&logoColor=white)
![Eureka](https://img.shields.io/badge/Eureka-6DB33F?style=for-the-badge&logo=Eureka&logoColor=white)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?style=for-the-badge&logo=Apache%20Kafka&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logoColor=white)
![JUnit](https://img.shields.io/badge/JUnit-25A162?style=for-the-badge&logo=JUnit5&logoColor=white)

## 소개

Spring 기반으로 개발된 공연 티켓 판매 플랫폼의 백엔드 API입니다.

Docker를 활용하여 MSA 아키텍처로 설계했으며, 

**서비스 간 통신**, **데이터 일관성 및 신뢰성**, **성능 최적화**, **테스트 코드 작성**에 대해 고민하며 진행한 프로젝트입니다.

특히, **티켓 오픈 시점에 발생하는 대량의 주문을 동시성 문제 없이 빠르게 처리하기 위한 방법을 찾는데 중점**을 두었습니다.

## 실행 방법

**[ 사전 준비 사항 ]**
- Docker
- git

1. 저장소를 클론합니다.

```
git clone https://github.com/gomudayya/Ticketing-Platform.git
```

2. 터미널을 이용해 해당 저장소의 root디렉토리로 가서 `run.sh` 파일을 실행시킵니다.

```
./run.sh
```

**~~(application.yml 파일을 올려두어서 바로 실행이 가능합니다.)~~**

## API 명세

[API 명세 바로가기!](https://documenter.getpostman.com/view/24242551/2sAXjQ19e4)

(개인 포스트맨 Workspace에 임포트하면 손쉽게 사용 가능합니다)

## ERD

![image](https://github.com/user-attachments/assets/53efc3e8-29b8-47ec-adea-923fb76faebb)

## 서비스 아키텍처

![ticket-land-architecture drawio (1)](https://github.com/user-attachments/assets/6d531065-3468-44bc-bff4-5ee17e62500b)

## Wiki

**[ STUDY ]**

- [Redis를 이용해 동시성 제어하기](https://github.com/gomudayya/Ticketing-Platform/wiki/Redis%EB%A5%BC%20%EC%9D%B4%EC%9A%A9%ED%95%B4%20%EB%8F%99%EC%8B%9C%EC%84%B1%20%EC%A0%9C%EC%96%B4%ED%95%98%EA%B8%B0)

**[Trouble Shooting]** 

- [티켓팅 시나리오에 대한 부하테스트 및 성능개선](https://github.com/gomudayya/Ticketing-Platform/wiki/Redis%EB%A5%BC%20%EC%9D%B4%EC%9A%A9%ED%95%B4%20%EB%8F%99%EC%8B%9C%EC%84%B1%20%EC%A0%9C%EC%96%B4%ED%95%98%EA%B8%B0)


