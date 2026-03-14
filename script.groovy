def build() {
    echo 'building the application...'
    sh 'mvn -B clean install'
}
return this
