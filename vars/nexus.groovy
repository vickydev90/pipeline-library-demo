def publishNexus(String targetBranch, String targetEnv, context){
	node() {
		/*
		 * Your implementation for publishing the deployable artifact(s) to nexus.
		 * Can use library functions to make it easier
		 */
    	String artifact
 		String nexusURL = context.config.nexus.url ?: 'http://invalid.url/'

 		// Not needed nexusPublisher will use its own global creds
 		// unless you have custom needs
 		String customCredentials = context.config.nexus.credentials ?: null
 		try {
			dir ('j2'){
				deleteDir()
				unstash "artifact-${context.application}-${targetBranch}"
				artifact =  sh(returnStdout: true, script: 'ls *.tar.gz | head -1').trim()
		 		echo "PUBLISH: ${this.name()} artifact ${artifact} to ${nexusURL} "
				nexusPublisher {
					targetURL = nexusURL
					tarball = artifact
				}
			}
		} catch (error) {
 			echo "Failed to publish artifact to Nexus"
 		} finally {
 			//
 		}
	}
}
