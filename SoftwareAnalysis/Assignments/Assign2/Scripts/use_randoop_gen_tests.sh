RANDOOP_CLASSPATH="../Local-Jars/randoop-all-3.0.4.jar"
SRC_CLASSPATH="../AssignCoverage/bin"
IO_ARGS="--classlist=my_classes.txt --junit-output-dir=../AssignCoverage/test_src --junit-package-name=randoop"
LITERAL_ARGS="--literals-file=literals.txt"
TIME_LIMIT_ARGS=" --timelimit=600"

java -cp $RANDOOP_CLASSPATH:$SRC_CLASSPATH randoop.main.Main gentests $IO_ARGS $LITERAL_ARGS $TIME_LIMIT_ARGS
