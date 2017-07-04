grails.cache.config = {
    cache {
        name 'cacheDn'
        eternal false
        timeToIdleSeconds 300
        timeToLiveSeconds 300
        overflowToDisk true
        maxElementsInMemory 10000
        maxElementsToDisk 1000000
    }
    cache {
        name 'cacheListVersions'
        eternal false
        timeToIdleSeconds 3000
        timeToLiveSeconds 3000
        overflowToDisk true
        maxElementsInMemory 1000
        maxElementsToDisk 10000
    }
    cache {
        name 'cacheListApps'
        eternal false
        timeToIdleSeconds 3000
        timeToLiveSeconds 3000
        overflowToDisk false
        maxElementsInMemory 1
        maxElementsToDisk 0
    }
    cache {
        name 'cacheListRepos'
        eternal false
        timeToIdleSeconds 3000
        timeToLiveSeconds 3000
        overflowToDisk false
        maxElementsInMemory 1000
        maxElementsToDisk 1000
    }
}