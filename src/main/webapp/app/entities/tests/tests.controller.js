(function () {
    'use strict';

    angular
        .module('smartTestUiApp')
        .controller('JhiTestsController', JhiTestsController);

    JhiTestsController.$inject = ['$cookies', '$http', 'JhiTestsService'];

    function JhiTestsController ($cookies, $http, JhiTestsService) {
        // This controller uses a Websocket connection to receive user activities in real-time.
        var vm = this;

        vm.tests = [];
        console.log("c y s")
        JhiTestsService.subscribe();
        JhiTestsService.connect();

        // JhiTestsService.receive().then(null, null, function(test) {
        //     console.log(test)
        //     vm.tests.push(test);
        // });

    }
})();
