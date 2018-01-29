build: compile

test:
	./gradlew clean generateProto test

compile:
	./gradlew clean generateProto compileKotlin compileJava

run:
	./gradlew clean generateProto bootRun
