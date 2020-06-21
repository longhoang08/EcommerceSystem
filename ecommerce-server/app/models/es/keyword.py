# coding=utf-8
import logging

__author__ = 'LongHB'
_logger = logging.getLogger(__name__)

mappings = {
    "properties": {
        "count": {
            "type": "integer"
        },
        "id": {
            "type": "keyword"
        },
        "keyword": {
            "type": "text",
            "fields": {
                "raw": {
                    "type": "keyword"
                }
            }
        },
        "keyword_no_tone": {
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
