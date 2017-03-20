'use strict';

describe('Controller Tests', function() {

    describe('Header Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockHeader, MockTipoHeader, MockServiceProvider;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockHeader = jasmine.createSpy('MockHeader');
            MockTipoHeader = jasmine.createSpy('MockTipoHeader');
            MockServiceProvider = jasmine.createSpy('MockServiceProvider');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Header': MockHeader,
                'TipoHeader': MockTipoHeader,
                'ServiceProvider': MockServiceProvider
            };
            createController = function() {
                $injector.get('$controller')("HeaderDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'smartTestUiApp:headerUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
