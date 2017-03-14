(function() {
    'use strict';

    angular
        .module('smartTestUiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('servicio', {
            parent: 'entity',
            url: '/servicio',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.servicio.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/servicio/servicios.html',
                    controller: 'ServicioController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('servicio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('servicio-detail', {
            parent: 'entity',
            url: '/servicio/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'smartTestUiApp.servicio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/servicio/servicio-detail.html',
                    controller: 'ServicioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('servicio');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Servicio', function($stateParams, Servicio) {
                    return Servicio.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'servicio',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('servicio-detail.edit', {
            parent: 'servicio-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/servicio/servicio-dialog.html',
                    controller: 'ServicioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Servicio', function(Servicio) {
                            return Servicio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('servicio.new', {
            parent: 'servicio',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/servicio/servicio-dialog.html',
                    controller: 'ServicioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                url: null,
                                activo: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('servicio', null, { reload: 'servicio' });
                }, function() {
                    $state.go('servicio');
                });
            }]
        })
        .state('servicio.edit', {
            parent: 'servicio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/servicio/servicio-dialog.html',
                    controller: 'ServicioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Servicio', function(Servicio) {
                            return Servicio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('servicio', null, { reload: 'servicio' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('servicio.delete', {
            parent: 'servicio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/servicio/servicio-delete-dialog.html',
                    controller: 'ServicioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Servicio', function(Servicio) {
                            return Servicio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('servicio', null, { reload: 'servicio' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
