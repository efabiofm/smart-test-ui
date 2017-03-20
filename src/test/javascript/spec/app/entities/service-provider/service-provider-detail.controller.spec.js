'use strict';

describe('Controller Tests', function() {

    describe('ServiceProvider Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockServiceProvider, MockHeader;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockServiceProvider = jasmine.createSpy('MockServiceProvider');
            MockHeader = jasmine.createSpy('MockHeader');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ServiceProvider': MockServiceProvider,
                'Header': MockHeader
            };
            createController = function() {
                $injector.get('$controller')("ServiceProviderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:serviceProviderUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
