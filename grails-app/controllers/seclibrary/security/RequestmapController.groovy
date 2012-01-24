package seclibrary.security

import grails.plugins.springsecurity.SpringSecurityService;

import org.springframework.dao.DataIntegrityViolationException

class RequestmapController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	
	SpringSecurityService springSecurityService

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [requestmapInstanceList: Requestmap.list(params), requestmapInstanceTotal: Requestmap.count()]
    }

    def create() {
        [requestmapInstance: new Requestmap(params)]
    }

    def save() {
        def requestmapInstance = new Requestmap(params)
        if (!requestmapInstance.save(flush: true)) {
            render(view: "create", model: [requestmapInstance: requestmapInstance])
            return
        }
		springSecurityService.clearCachedRequestmaps()
		flash.message = message(code: 'default.created.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), requestmapInstance.id])
        redirect(action: "show", id: requestmapInstance.id)
    }

    def show() {
        def requestmapInstance = Requestmap.get(params.id)
        if (!requestmapInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), params.id])
            redirect(action: "list")
            return
        }

        [requestmapInstance: requestmapInstance]
    }

    def edit() {
        def requestmapInstance = Requestmap.get(params.id)
        if (!requestmapInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), params.id])
            redirect(action: "list")
            return
        }

        [requestmapInstance: requestmapInstance]
    }

    def update() {
        def requestmapInstance = Requestmap.get(params.id)
        if (!requestmapInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (requestmapInstance.version > version) {
                requestmapInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'requestmap.label', default: 'Requestmap')] as Object[],
                          "Another user has updated this Requestmap while you were editing")
                render(view: "edit", model: [requestmapInstance: requestmapInstance])
                return
            }
        }

        requestmapInstance.properties = params

        if (!requestmapInstance.save(flush: true)) {
            render(view: "edit", model: [requestmapInstance: requestmapInstance])
            return
        }
		springSecurityService.clearCachedRequestmaps()
		flash.message = message(code: 'default.updated.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), requestmapInstance.id])
        redirect(action: "show", id: requestmapInstance.id)
    }

    def delete() {
        def requestmapInstance = Requestmap.get(params.id)
        if (!requestmapInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), params.id])
            redirect(action: "list")
            return
        }

        try {
            requestmapInstance.delete(flush: true)
			springSecurityService.clearCachedRequestmaps()
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
