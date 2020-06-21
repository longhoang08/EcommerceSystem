# coding=utf-8
import logging

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

mappings = {
    "properties": {
        "id": {
            "type": "integer"
        },
        "parent_id": {
            "type": "integer"
        },
        "is_last_level": {
            "type": "boolean"
        },
        "level": {
            "type": "integer"
        },
        "code": {
            "type": "keyword"
        },
        "name": {
            "type": "text",
            "fields": {
                "raw": {
                    "type": "keyword"
                }
            }
        },
        "name_no_tone": {
            "type": "text",
            "fields": {
                "raw": {
                    "type": "keyword"
                }
            }
        }
    }
}

settings = {}
