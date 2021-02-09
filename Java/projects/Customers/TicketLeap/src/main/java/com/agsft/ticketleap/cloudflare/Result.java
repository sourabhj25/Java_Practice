package com.agsft.ticketleap.cloudflare;

import java.util.ArrayList;

public class Result {
	private String id;

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String type;

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String content;

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	private String proxiable;

	public String getProxiable() {
		return this.proxiable;
	}

	public void setProxiable(String proxiable) {
		this.proxiable = proxiable;
	}

	private String proxied;

	public String getProxied() {
		return this.proxied;
	}

	public void setProxied(String proxied) {
		this.proxied = proxied;
	}

	private String ttl;

	public String getTtl() {
		return this.ttl;
	}

	public void setTtl(String ttl) {
		this.ttl = ttl;
	}

	private String locked;

	public String getLocked() {
		return this.locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	private String zone_id;

	public String getZoneId() {
		return this.zone_id;
	}

	public void setZoneId(String zone_id) {
		this.zone_id = zone_id;
	}

	private String zone_name;

	public String getZoneName() {
		return this.zone_name;
	}

	public void setZoneName(String zone_name) {
		this.zone_name = zone_name;
	}

	private String modified_on;

	public String getModifiedOn() {
		return this.modified_on;
	}

	public void setModifiedOn(String modified_on) {
		this.modified_on = modified_on;
	}

	private String created_on;

	public String getCreatedOn() {
		return this.created_on;
	}

	public void setCreatedOn(String created_on) {
		this.created_on = created_on;
	}

	private Meta meta;

	public Meta getMeta() {
		return this.meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	@Override
	public String toString() {
		return "Result [id=" + id + ", type=" + type + ", name=" + name + ", content=" + content + ", proxiable="
				+ proxiable + ", proxied=" + proxied + ", ttl=" + ttl + ", locked=" + locked + ", zone_id=" + zone_id
				+ ", zone_name=" + zone_name + ", modified_on=" + modified_on + ", created_on=" + created_on + ", meta="
				+ meta + "]";
	}

}
